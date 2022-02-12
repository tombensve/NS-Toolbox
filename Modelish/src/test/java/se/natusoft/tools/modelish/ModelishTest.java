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

        TestModel testModel = Modelish.create( TestModel.class )
                .name("Tommy Svensson")
                .age(53)
                .address("Stockholm")
                ._lock();

        assert testModel.name().equals( "Tommy Svensson" );
        assert testModel.age() == 53;
        assert testModel.address().equals( "Stockholm" );

        try {
            testModel.name( "qwerty" );
            // This because shit can ALWAYS happen!
            throw new RuntimeException("This should not happen since model is locked.");
        }
        catch ( IllegalArgumentException iae ) {
            assert iae.getMessage().equals( "Update of read only object not allowed!" );
        }
    }

    @Test
    public void verifyCloningModel() {

        TestModel testModel = Modelish.create( TestModel.class )
                .name("Tommy Svensson")
                .age(53)
                .address("Stockholm")
                ._lock();

        // Note that new model is not locked even if old one was.
        TestModel clonedModel = testModel._clone().address("Liljeholmen")._lock();

        // Make sure the original hasn't changed.
        assert testModel.address().equals( "Stockholm" );

        // The cloned should have the updated value.
        assert clonedModel.address().equals( "Liljeholmen" );

        // Make sure clone is locked.
        try {
            clonedModel.name("Tommy B Svensson");
        }
        catch ( IllegalArgumentException iae ) {
            assert iae.getMessage().equals( "Update of read only object not allowed!" );
        }

    }
}
