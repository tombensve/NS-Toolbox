/*
 *
 * PROJECT
 *     Name
 *         Modelish
 *
 *     Description
 *         Provides a RPN Query against name value set of data (Properties, Map).
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
package se.natusoft.tools.modelish;

import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;

/**
 * This doubles as test and example of usage.
 */
public class ModelishTest {

    //
    // Simple Usage
    //

    public interface UserInfo extends CloneableModelishModel<UserInfo> {

        String name();

        UserInfo name( String name );

        int age();

        UserInfo age( int age );

        String address();

        UserInfo address( String address );

    }

    @Test
    public void verifyModelishNormalUsage() {

        UserInfo userInfo = Modelish.create( UserInfo.class )
                .name( "Tommy Svensson" )
                .age( 53 )
                .address( "Stockholm" )
                ._lock();

        assert userInfo.name().equals( "Tommy Svensson" );
        assert userInfo.age() == 53;
        assert userInfo.address().equals( "Stockholm" );

        try {
            userInfo.name( "qwerty" );
            // This because shit can ALWAYS happen!
            throw new RuntimeException( "This should not happen since model is locked." );
        } catch ( IllegalArgumentException iae ) {
            assert iae.getMessage().equals( "Update of read only object not allowed!" );
        }
    }


    //
    // Cloning a model
    //

    @Test
    public void verifyCloningModel() {

        UserInfo userInfo = Modelish.create( UserInfo.class )
                .name( "Tommy Svensson" )
                .age( 53 )
                .address( "Stockholm" )
                ._lock();

        // Note that new model is not locked even if old one was.
        UserInfo clonedModel = userInfo._clone().address( "Liljeholmen" )._lock();

        // Make sure the original hasn't changed.
        assert userInfo.address().equals( "Stockholm" );

        // The cloned should have the updated value.
        assert clonedModel.address().equals( "Liljeholmen" );

        // Old, copied values are still there.
        assert clonedModel.name().equals( "Tommy Svensson" );
        assert clonedModel.age() == 53;

        // Make sure clone is locked.
        try {
            clonedModel.name( "Tommy B Svensson" );
        } catch ( IllegalArgumentException iae ) {
            assert iae.getMessage().equals( "Update of read only object not allowed!" );
        }

    }

    //
    // Adding a sub model
    //

    public interface User extends CloneableModelishModel<User> {

        String id();

        User id( String id );

        int loginCount();

        User loginCount( int count );

        UserInfo userInfo();

        User userInfo( UserInfo userInfo );

    }

    @Test
    public void verifySubModelish() {

        User user = Modelish.create( User.class )
                .id( "tbs" )
                .loginCount( 9843 )
                .userInfo( Modelish.create( UserInfo.class )
                        .name( "Tommy" )
                        .address( "Liljeholmen" )
                        .age( 53 )
                        ._lock()
                )
                ._lock();

        // Verify read access of model in model.
        assert user.userInfo().address().equals( "Liljeholmen" );
    }

    //
    // Cloning with a sub model
    //

    @Test
    public void verifySubModelishClone() {

        User user = Modelish.create( User.class )
                .id( "tbs" )
                .loginCount( 9843 )
                .userInfo( Modelish.create( UserInfo.class )
                                .name( "Tommy" )
                                .address( "Liljeholmen" )
                                .age( 53 )
                        //._lock()
                )
                ._lock();

        User user2 = user._clone();

        // Verify read access of model in model of cloned object.
        assert user2.userInfo().address().equals( "Liljeholmen" );

        // Modify original address
        user.userInfo().address( "Stockholm" );
        assert user.userInfo().address().equals( "Stockholm" );

        // Make sure address of clone is not modified.
        assert user2.userInfo().address().equals( "Liljeholmen" );

    }

    //
    // Recursive lock of model.
    //

    @Test
    public void verifyRecursiveLock() {

        User user = Modelish.create( User.class )
                .id( "tbs" )
                .loginCount( 9843 )
                .userInfo( Modelish.create( UserInfo.class )
                        .name( "Tommy" )
                        .address( "Liljeholmen" )
                        .age( 53 )
                )
                ._recursiveLock();

        try {
            user.userInfo().age( 43 );

            throw new IllegalArgumentException( "Update allowed! Should have caused an exception!" );

        } catch ( IllegalArgumentException iae ) {

            assert iae.getMessage().equals( "Update of read only object not allowed!" );
        }
    }

    //
    // Giving it a more builder feel
    //

    public interface Address extends CloneableModelishModel<Address> {

        String street();

        int streetNumber();

        String postalAddress();

    }

    public interface AddressBuilder extends Address {

        AddressBuilder street( String street );

        AddressBuilder streetNumber( int number );

        AddressBuilder postalAddress( String postalNumber );
    }

    @Test
    public void verifyBuilderStyle() {

        Address address = Modelish.create( AddressBuilder.class )
                .street( "Somewhere Street" )
                .streetNumber( 44 )
                .postalAddress( "Stockholm" )
                ._lock();


        // Note that these resulting objects are read only, only having getters.
        assert address.street().equals( "Somewhere Street" );
        assert address.streetNumber() == 44;
        assert address.postalAddress().equals( "Stockholm" );

    }

    //
    // Factory usage
    //

    public interface Car extends FactoryModelishModel<Car> {

        String model();
        @Nonnull Car model( String model );

        int age();
        Car age( int age );

        int wheels();
        Car wheels( int age );
    }


    @Test
    public void verifyFactoryUse() {

        Car carFactory = Modelish.create( Car.class ).age( 16 )._lock();

        // Note: _create() and _clone() are identical!! _create() just looks better when used as factory.
        Car car = carFactory._create().model( "VW EOS" ).wheels( 4 )._lock();

        assert car.model().equals( "VW EOS" );
        assert car.age() == 16;
        assert car.wheels() == 4;

    }

    //
    // Non null fields
    //

    @Test
    public void verifyNonnull() {

        boolean threwException = false;

        try {
            Car car = Modelish.create( Car.class ).model( null ).age( 16 ).wheels( 4 )._lock();
        }
        catch ( IllegalArgumentException iae ) {

            assert iae.getMessage().equals( "null passed to non nullable value!" );

            threwException = true;
        }

        assert threwException;
    }

}
