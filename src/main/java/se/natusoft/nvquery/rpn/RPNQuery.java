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
package se.natusoft.nvquery.rpn;

import se.natusoft.nvquery.api.DataQuery;
import se.natusoft.nvquery.api.QueryData;
import se.natusoft.nvquery.api.Operation;
import se.natusoft.nvquery.rpn.operations.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Provides the user API for RPNQuery.
 */
public class RPNQuery implements DataQuery
{
    //
    // Statics
    //

    /** A set of operations. */
    private static Map<String, Operation> _operations_ = initOps();

    private static Map<String, Operation> initOps()
    {

        Map<String, Operation> operations = new LinkedHashMap<>();
        operations.put( "()", new Contains() );
        operations.put( "!()", new NotContains() );
        operations.put( "=", new Equals() );
        operations.put( "!=", new NotEquals() );
        operations.put( "T", new True() );
        operations.put( "F", new False() );
        operations.put( ">", new GreaterThan() );
        operations.put( ">=", new GreaterThanEquals() );
        operations.put( "<", new LessThan() );
        operations.put( "<=", new LessThanEquals() );

        return operations;
    }

    /**
     * Provide operation implementation.
     *
     * @param operation The query string syntax of operation.
     *
     * @return The implementation of operation.
     */
    private static Operation lookupOperation( String operation )
    {
        return _operations_.get( operation );
    }

    //
    // Constructors
    //

    /**
     * Creates a new RPNQuery instance.
     */
    public RPNQuery()
    {
    }

    //
    // Methods
    //

    /**
     * Makes a query returning a String of 'T' or 'F' as long as query ends up with one value in stack.
     *
     * @param query The query to make.
     * @param queryData The data to query.
     *
     * @return true or false.
     *
     * @exception IllegalStateException on reference if non existent value.
     */
    public boolean query( String query, QueryData queryData )
    {

        Stack<String> queryStack = new Stack<>();

        Arrays.stream( query.split( " " ) ).iterator().forEachRemaining( value -> {

            // Replace all '_' to spaces. The query must contain '_' instead of space to parse correct.
            value = value.replace( "_", " " );

            if ( value.startsWith( "/" ) ) { // An operation

                String operation = value.substring( 1 ).trim();
                Operation op = lookupOperation( operation );
                if ( op == null ) throw new IllegalStateException( "Unknown operation: " + operation + "!" );

                boolean res = false;
                String val2 = queryStack.pop();
                if ( !queryStack.empty() ) {
                    String val1 = queryStack.pop();
                    res = op.execute( val1, val2 );
                }
                else { // This basically only happens for True and False.
                       // This also means that there is only one entry on the stack and that
                       // is principally wrong! But I accept this anyhow treating the first
                       // value as a true. This will happen when you end up with one true
                       // and want to verify it without first pushing another true and then
                       // do an equals instead, which would be more proper.
                       // I basically support a lazy and bad query.
                       // The True and False operations are basically one value operations
                       // breaking the pattern. Should maybe remove these.
                    res = op.execute( "T", val2 );
                }

                queryStack.push( res ? "T" : "F" );
            }
            else if ( value.startsWith( "'" ) ) { // String value

                value = value.replaceAll( "'", "" );
                queryStack.push( value );
            }
            // Regexp from https://stackoverflow.com/questions/2811031/decimal-or-numeric-values-in-regular-expression-validation
            else if ( value.matches( "^[1-9]\\d*(\\.\\d+)?$" ) ) { // A number
                queryStack.push( value );
            }
            else { // A property reference.

                String valueName = value;
                value = queryData.getByName( valueName );
                if ( value == null ) throw new IllegalStateException( "'" + valueName + "' does not exist!" );
                queryStack.push( value );
            }
        } );

        return queryStack.pop().trim().equals( "T" );
    }
}
