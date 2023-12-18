/*
 *
 * PROJECT
 *     Name
 *         Modelish
 *
 *     Description
 *         Builds models both fluent and Java Bean using only interfaces.
 *
 * COPYRIGHTS
 *     Copyright (C) 2022 by Natusoft AB All rights reserved.
 *
 * LICENSE
 *     Apache 2.0 (Open Source)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 * AUTHORS
 *     tommy ()
 *         Changes:
 *         2022-02-11: Created!
 *
 */
package se.natusoft.tools.modelish

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Test


/**
 * This doubles as test and example of usage.
 */
@CompileStatic
class ModelishTest {

    //
    // Simple Usage
    //

    interface UserInfo extends Cloneable<UserInfo> {

        String name()

        UserInfo name( String name )

        int age();

        UserInfo age( int age )

        String address();

        UserInfo address( String address )

    }

    @Test
    void verifyModelishNormalUsage() {

        UserInfo userInfo = Modelish.create( UserInfo.class )
                .name( "Tommy Svensson" )
                .age( 53 )
                .address( "Stockholm" )
                ._immutable()

        assert userInfo.name() == "Tommy Svensson"
        assert userInfo.age() == 53
        assert userInfo.address() == "Stockholm"

        try {
            userInfo.name( "qwerty" )
            // This because shit can ALWAYS happen!
            throw new RuntimeException( "This should not happen since model is locked." )
        }
        catch ( IllegalArgumentException iae ) {
            assert iae.getMessage() == "Update of read only object not allowed!"
        }

    }

    @Test
    void verifyToStringAndHashCode() {
        UserInfo userInfo = Modelish.create( UserInfo.class )
                .name( "Tommy Svensson" )
                .age( 53 )
                .address( "Stockholm" )

        // I am assuming here that hashCode() will be the same independent of JVM and version ...
        println "toString:"
        println "  " + userInfo.toString()
        println ""
        println "hashCode:"
        println "  " + userInfo.hashCode(  )

        assert userInfo.toString(  ) == "[ Tommy Svensson, 53, Stockholm ]"
        assert userInfo.hashCode(  ) == -1027867531

        userInfo.name("Tommy Bengt Svensson")
        println " new hashCode: " + userInfo.hashCode(  )

        assert userInfo.hashCode(  ) == 1376967037
    }


    //
    // Cloning a model
    //

    @Test
    void verifyCloningModel() {

        UserInfo userInfo = Modelish.create( UserInfo.class )
                .name( "Tommy Svensson" )
                .age( 53 )
                .address( "Stockholm" )
                ._immutable()

        // Note that new model is not locked even if old one was.
        UserInfo clonedModel = userInfo._clone().address( "Liljeholmen" )._immutable()

        // Make sure the original hasn't changed.
        assert userInfo.address() == "Stockholm"

        // The cloned should have the updated value.
        assert clonedModel.address() == "Liljeholmen"

        // Old, copied values are still there.
        assert clonedModel.name() == "Tommy Svensson"
        assert clonedModel.age() == 53

        // Make sure clone is locked.
        try {
            clonedModel.name( "Tommy B Svensson" )
        }
        catch ( IllegalArgumentException iae ) {
            assert iae.getMessage() == "Update of read only object not allowed!"
        }

    }

    //
    // Adding a sub model
    //

    interface User extends Cloneable<User> {

        @SuppressWarnings( 'unused' )
        String id()

        User id( String id )

        @SuppressWarnings( 'unused' )
        int loginCount();

        User loginCount( int count )

        UserInfo userInfo();

        @NoNull
        User userInfo( UserInfo userInfo )

    }

    @Test
    void verifySubModelish() {

        User user = Modelish.create( User.class )
                .id( "tbs" )
                .loginCount( 9843 )
                .userInfo( Modelish.create( UserInfo.class )
                        .name( "Tommy" )
                        .address( "Liljeholmen" )
                        .age( 53 )
                        ._immutable()
                )
                ._immutable()

        // Verify read access of model in model.
        assert user.userInfo().address() == "Liljeholmen"
    }

    //
    // Cloning with a sub model
    //

    @Test
    void verifySubModelishClone() {

        User user = Modelish.create( User.class )
                .id( "tbs" )
                .loginCount( 9843 )
                .userInfo( Modelish.create( UserInfo.class )
                        .name( "Tommy" )
                        .address( "Liljeholmen" )
                        .age( 53 )
                        //._lock()
                )
                ._immutable()

        User user2 = user._clone()

        // Verify read access of model in model of cloned object.
        assert user2.userInfo().address() == "Liljeholmen"

        // Modify original address
        user.userInfo().address( "Stockholm" )
        assert user.userInfo().address() == "Stockholm"

        // Make sure address of clone is not modified.
        assert user2.userInfo().address() == "Liljeholmen"

    }

    //
    // Recursive lock of model.
    //

    @Test
    void verifyRecursiveLock() {

        User user = Modelish.create( User.class )
                .id( "tbs" )
                .loginCount( 9843 )
                .userInfo( Modelish.create( UserInfo.class )
                        .name( "Tommy" )
                        .address( "Liljeholmen" )
                        .age( 53 )
                )
                ._recursivelyImmutable()

        try {
            user.userInfo().age( 43 )

            throw new IllegalArgumentException( "Update allowed! Should have caused an exception!" )

        }
        catch ( IllegalArgumentException iae ) {

            assert iae.getMessage() == "Update of read only object not allowed!"
        }
    }

    //
    // Giving it a more builder feel
    //

    interface Address extends Cloneable<Address> {

        String street()

        int streetNumber()

        String postalAddress()

    }

    interface AddressBuilder extends Address {

        AddressBuilder street( String street )

        AddressBuilder streetNumber( int number )

        AddressBuilder postalAddress( String postalNumber )
    }

    @Test
    void verifyBuilderStyle() {

        Address address = Modelish.create( AddressBuilder.class )
                .street( "Somewhere Street" )
                .streetNumber( 44 )
                .postalAddress( "Stockholm" )
                ._immutable()


        // Note that these resulting objects are read only, only having getters.
        // But yes, the instance can still be cast to AddressBuilder also, but any
        // try to modify will fail due to the ending _lock() call.
        // But using the Address interface only gives you access to getters, thus looking
        // a bit cleaner.
        assert address.street() == "Somewhere Street"
        assert address.streetNumber() == 44
        assert address.postalAddress() == "Stockholm"

    }

    //
    // Factory usage
    //

    interface Car extends Factory<Car> {

        String model();

        @NoNull
        Car model( String model )

        int age();

        Car age( int age )

        int wheels();

        Car wheels( int age )
    }


    @Test
    void verifyFactoryUse() {

        Car carFactory = Modelish.create( Car.class ).age( 16 )._immutable()

        // Note: _create() and _clone() are identical!! _create() just looks better when used as factory.
        Car car = carFactory._create().model( "VW EOS" ).wheels( 4 )._immutable()

        assert car.model() == "VW EOS"
        assert car.age() == 16
        assert car.wheels() == 4

    }

    //
    // Not null fields
    //

    @Test
    void verifyNonnull() {

        boolean threwException = false

        try {
            Modelish.create( Car.class ).model( null ).age( 16 ).wheels( 4 )._immutable()
        }
        catch ( IllegalArgumentException iae ) {

            assert iae.getMessage() == "null passed to non nullable 'model'!"

            threwException = true
        }

        assert threwException
    }

    //
    // Java Bean methods (Well, not 100%, setters have to return the interface type!).
    //

    interface JBTest extends Model<JBTest> {

        JBTest setName( String name )

        String getName()

        JBTest setLocationId( int locationId )

        int getLocationId()
    }

    @Test
    void verifyJavaBean() {

        JBTest jbTest = Modelish.create( JBTest.class ).setName( "Nisse" ).setLocationId( 22 )._immutable()

        assert jbTest.getName() == "Nisse"
        assert jbTest.name == "Nisse" // Groovy property access work.
        assert jbTest.getLocationId() == 22

        try {
            jbTest.setName( "hult" )

            throw new ModelishException( "This should not happen!" )
        }
        catch ( ModelishException me ) {

            assert me.getMessage() == "Update of read only object not allowed!"
        }
    }

    /*
     * DO NOTE in above test, that if a @NoNull annotated setter is never called then the result of
     * getting that value will be null!!! I see no clean way to solve this. It would be possible to add
     * a _validate() method to Model interface, but still messy to implement since it requires information
     * not available in proxy handler call, and assumptions have to be made. This is currently rather clean,
     * and I will not mess it up due to this little "issue".
     */

    @Test
    void verifyGetValueMap() {

        User user = Modelish.create( User.class )
                .id( "tbs" )
                .loginCount( 9843 )
                .userInfo( Modelish.create( UserInfo.class )
                        .name( "Tommy" )
                        .address( "Liljeholmen" )
                        .age( 54 )
                        ._immutable()
                )
                ._immutable()

        Map<String, Object> map = user._toMap()

        assert map[ "id" ] == "tbs"
        assert map[ "loginCount" ] == 9843
        assert map[ "userInfo" ] instanceof Map
        assert map[ "userInfo" ][ "name" ] == "Tommy"
        assert map[ "userInfo" ][ "address" ] == "Liljeholmen"
        assert map[ "userInfo" ][ "age" ] as int == 54
    }

    @Test
    void verifySettingFromMap() {

        // If the following line have "userMap" red underlined, you are using IDEA! Ignore it, this compiles.
        //
        // Notice that it also handles the inner userInfo map without casting!
        //
        // I believe this is so that you can use JSON:ish Map structures without forcing a lot of casts.
        // That of course make it up to the developer to make sure it is correct! In this case other code
        // will fail if you don't have correct data in the Map, which will cause test to fail.

        Map<String, Object> userMap = [
                "id"        : "tbs",
                "loginCount": 9972,
                "userInfo"  : [
                        "name"   : "Tommy Svensson",
                        "address": "Liljeholmen",
                        "age"    : 54
                ]
        ] as Map<String, Object>

        User user = Modelish.createFromMap( User.class as Class<Model>, userMap ) as User

        assert user.id() == "tbs"
        assert user.loginCount() == 9972
        assert user.userInfo().name() == "Tommy Svensson"
        assert user.userInfo().address() == "Liljeholmen"
        assert user.userInfo().age() == 54
    }

    @Test
    void verifyNotNullFromMap() {

        Map<String, Object> userMap = [
                "id"        : "tbs",
                "loginCount": 9972
        ] as Map<String, Object>

        try {
            User user = Modelish.createFromMap( User.class as Class<Model>, userMap ) as User

            throw new RuntimeException("This should not happen!")
        }
        catch ( IllegalArgumentException iae ) {

            assert iae.message == "Property 'userInfo' cannot be null!"
        }

    }

    interface MixedFormatTestData extends Cloneable<MixedFormatTestData> {

        MixedFormatTestData someData(String data)
        MixedFormatTestData setSomeData(String data)

        String getSomeData()
    }

    @Test
    void verifyMixedFormatSetterAndGetter() {

        MixedFormatTestData mftd = Modelish.create(MixedFormatTestData.class)

        mftd.someData("qwerty")
        assert mftd.someData == "qwerty"
        assert mftd.getSomeData() == "qwerty"

        // The problem here is that you cannot chain setters!
        mftd.someData = "asdf"
        assert mftd.someData == "asdf"
    }
}
