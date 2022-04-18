/*
 *
 * PROJECT
 *     Name
 *         RPNQuery
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
 *         2021-09-05: Created!
 *
 */
package se.natusoft.toolbox.nvquery.rpn.operations

import groovy.transform.CompileStatic
import se.natusoft.toolbox.api.query.Operation

@CompileStatic
class NotEquals extends Equals implements Operation {
    /**
     * Executes the operation on the 2 provided values.
     *
     * @param value1 First value.
     * @param value2 Second value.
     * @return true or false.
     */
    @Override
    boolean execute( String value1, String value2 ) {

        !super.execute( value1, value2 )
    }
}
