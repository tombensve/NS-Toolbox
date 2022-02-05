/*
 *
 * PROJECT
 *     Name
 *         ns-toolbox-apis
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
package se.natusoft.toolbox.api.query;

/**
 * API for providing data to query.
 */
public interface QueryData {

    /**
     * Should return data for the given name.
     *
     * @param name The named data to get.
     * @return The value of the named data.
     */
    String getByName( String name );
}
