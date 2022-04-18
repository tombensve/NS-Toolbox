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
 *     Licensed under the Apache License, Version 2.0 (the "License")
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
 *         2021-09-30: Created!
 *
 */
package se.natusoft.nvquery

import org.junit.jupiter.api.Test
import se.natusoft.toolbox.api.query.DataQueryProvider
import se.natusoft.toolbox.data.providers.MapQueryData
import se.natusoft.toolbox.nvquery.rpn.RPNQuery

class RPNQueryTests {
    private final DataQueryProvider query = new RPNQuery()

    private final MapQueryData mapQueryData = new MapQueryData()
            .add( "name", "test data" )
            .add( "version", "1.0" )
            .add( "int", "8" )
            .add( "boolt", "true" )
            .add( "boolf", "false" )

    //
    // Basic tests
    //

    @Test
    void testEquals() {
        assert query.query( "name 'test_data' /=", mapQueryData )// Spaces must be '_'!
    }

    @Test
    void testEqualsNumber() {
        assert query.query( "version 1.0 /=", mapQueryData )
    }

    @Test
    void testNotEquals() {
        assert query.query( "int 7 /!=", mapQueryData )
        assert !query.query( "int 8 /!=", mapQueryData )
    }

    @Test
    void testContains() {
        assert query.query( "name 'data' /()", mapQueryData )
    }

    @Test
    void testNotContains() {
        assert query.query( "name 'Nisse' /!()", mapQueryData )
    }

    @Test
    void testTrue() {
        assert query.query( "boolt /T", mapQueryData )
    }

    @Test
    void testFalse() {
        assert query.query( "boolf /F", mapQueryData )
    }

    @Test
    void testGreaterThan() {
        assert query.query( "int 5 />", mapQueryData )
    }

    @Test
    void testLessThan() {
        assert query.query( "int 10 /<", mapQueryData )
    }

    @Test
    void testGreaterThanEquals() {
        assert query.query( "int 8 />=", mapQueryData )
    }

    @Test
    void testLessThanEquals() {
        assert query.query( "int 8 /<=", mapQueryData )
    }

    //
    // Complex tests
    //

    @Test
    void testComplex() {
        MapQueryData data = new MapQueryData()
                .add( "name", "MyServiceId" )
                .add( "type", "service" )
                .add( "qwe", 92 )
                .add( "rty", 236 )

        assert query.query(
                ///---------1--------\ /---2----\ /3\/---4----\ /5\/------6-----\ /7\/8\/9\
                "name 'MyServiceId' /= qwe 100 /< /= rty 100 /> /= type 'service' /= /= /T",
                data
        )
        // 1. Value of name and 'MyServiceId' is equal. ==> true
        // 2. Value of qwe is less than 100. ==> true
        // 3. The last 2 operations have the same result (true in this case). Leaves one 'true' on the stack.
        // 4. Value of rty is greater than 100. ==> true
        // 5. The last 2 operations have same result (true).
        // 6. type contains 'service'.
        // 7. The last 2 operations have same result (true).
        // 8. The last 2 operations have same result (true). Shortening the stack.
        // 9. Checks that end result (last entry on stack) is true (should only be one true on the stack here).
    }
}
