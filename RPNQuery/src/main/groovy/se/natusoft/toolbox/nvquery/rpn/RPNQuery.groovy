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
 *         2021-09-05: Created!
 *
 */
package se.natusoft.toolbox.nvquery.rpn

import groovy.transform.CompileStatic
import se.natusoft.toolbox.api.query.DataQueryProvider
import se.natusoft.toolbox.api.query.Operation
import se.natusoft.toolbox.api.query.QueryData
import se.natusoft.toolbox.api.query.SingleValueOperation
import se.natusoft.toolbox.nvquery.rpn.operations.*

/**
 * Provides the user API for RPNQuery.
 */
@CompileStatic
class RPNQuery implements DataQueryProvider {

    //
    // Statics
    //

    /** A set of operations. */
    private static final Map<String, Operation> OPERATIONS = [
            "()" : new Contains(),
            "!()": new NotContains(),
            "="  : new Equals(),
            "!=" : new NotEquals(),
            "T"  : new True(),
            "F"  : new False(),
            ">"  : new GreaterThan(),
            ">=" : new GreaterThanEquals(),
            "<"  : new LessThan(),
            "<=" : new LessThanEquals()

    ] as Map<String, Operation>

    //
    // Constructors
    //

    /**
     * Creates a new RPNQuery instance.
     */
    RPNQuery() {}

    //
    // Methods
    //

    /**
     * Makes a query returning a String of 'T' or 'F' as long as query ends up with one value in stack.
     *
     * @param query The query to make.
     * @param queryData The data to query.
     * @return true or false.
     * @exception IllegalStateException on reference if non existent value.
     */
    boolean query( String query, QueryData queryData ) {

        Stack<String> queryStack = new Stack<>()

        def isAnOperation = { String value -> value.startsWith( "/" ) }
        def isAValue = { String value -> value.startsWith( "'" ) }
        def isANumber = { String value -> value ==~ /^[1-9]\d*(\.\d+)?$/ }
        // Regexp from https://stackoverflow.
        // com/questions/2811031/decimal-or-numeric-values-in-regular-expression-validation

        query.split( / / ).findAll { String value ->

            // Replace all '_' to spaces. The query must contain '_' instead of space to parse correct.
            value = value.replace( "_", " " )

            if ( isAnOperation value ) {

                String operation = value.substring( 1 ).trim()
                Operation op = OPERATIONS[ operation ]

                if ( op == null ) {
                    throw new IllegalStateException( "Unknown operation: " + operation + "!" )
                }

                boolean res
                String val2 = queryStack.pop()
                if ( !queryStack.empty() ) {

                    String val1 = queryStack.pop()
                    res = op.execute( val1, val2 )
                }
                else {
                    // This basically only happens for True and False.
                    // This also means that there is only one entry on the stack and that
                    // is principally wrong! But I accept this anyhow treating the first
                    // value as a true. This will happen when you end up with one true
                    // and want to verify it without first pushing another true and then
                    // do an equals instead, which would be more proper.
                    // I basically support a lazy and bad query.
                    // The True and False operations are basically one value operations
                    // breaking the pattern. Should maybe remove these.

                    if ( !( op instanceof SingleValueOperation ) ) {
                        throw new IllegalStateException( "Only one value provided to operation requiring two!" )
                    }

                    res = op.execute( val2, null )
                }

                queryStack.push( res ? "T" : "F" )
            }

            else if ( isAValue value ) {

                value = value.replaceAll( "'", "" )
                queryStack.push( value )
            }

            else if ( isANumber value  ) {

                queryStack.push( value )
            }
            else {
                // A property reference.

                String valueName = value
                value = queryData.getByName( valueName )
                if ( value == null ) {
                    throw new IllegalStateException( "'${valueName}' does not exist!" )
                }
                queryStack.push( value )
            }

        }

        queryStack.pop().trim() == "T"
    }
}
