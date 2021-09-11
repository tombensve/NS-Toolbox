/*
 *
 * PROJECT
 *     Name
 *         rpnquery
 *
 * COPYRIGHTS
 *     Copyright (C) 2021 by Natusoft AB All rights reserved.
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
package se.natusoft.nvquery.api;

/**
 * A rather generic query API, just for the heck of it ...
 */
public interface DataQuery {

    /**
     * Executes supplied query on supplied query data.
     *
     * @param query The query to make.
     * @param queryData The data to query.
     *
     * @return true or false.
     */
    boolean query( String query, QueryData queryData );
}
