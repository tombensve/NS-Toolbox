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

public class ModelishTest {

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

            throw new IllegalArgumentException("Update allowed! Should have caused an exception!");

        } catch ( IllegalArgumentException iae ) {

            assert iae.getMessage().equals( "Update of read only object not allowed!" );
        }
    }
}
