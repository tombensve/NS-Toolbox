package se.natusoft.query.rpn;

import se.natusoft.query.DataQuery;
import se.natusoft.query.QueryData;
import se.natusoft.query.rpn.operations.Equals;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Provides the user API for RPNQuery.
 */
public class RPNQuery implements DataQuery {
    //
    // Statics
    //

    // This would have been so much cleaner to do in Groovy, but I didn't want to pull in the
    // Groovy runtime just for this.

    /** A cache of operations. */
    private static Map<String, Operation> _operations_ = initOps();

    private static Map<String, Operation> initOps() {

        Map<String, Operation> ops = new LinkedHashMap<>();
        _operations_.put( "=", new Equals() );

        return ops;
    }

    private static Operation lookupOperation(String operation) {
        return _operations_.get( operation );
    }

    //
    // Private Members
    //

    /** The data that a query can reference by name. */
    private QueryData queryData;

    //
    // Constructors
    //

    public RPNQuery( QueryData queryData ) {
        this.queryData = queryData;

    }

    //
    // Methods
    //

    /**
     * Makes a query returning a String of 'T' or 'F' as long as query ends up with one value in stack.
     *
     * @param query The query to make.
     * @return "T" or "F".
     * @exception IllegalStateException on reference of non existent value.
     */
    public String query( String query ) throws IllegalStateException /* To be clear! */ {

        Stack<String> queryStack = new Stack<>();

        Arrays.stream( query.split( " " ) ).iterator().forEachRemaining( value -> {

            if ( value.startsWith( "/" ) ) {

                String operation = value.substring( 1 ).trim();
                Operation op = lookupOperation( operation );
                String val2 = queryStack.pop();
                String val1 = queryStack.pop();
                boolean res = op.execute( val1, val2 );

                queryStack.push( res ? "T" : "F" );
            }
            else if ( value.startsWith( "'" ) ) {

                value = value.replaceAll( "'", "" );
                queryStack.push( value );
            }
            else {

                String valueName = value;
                value = this.queryData.getByName( valueName );
                if ( value == null ) throw new IllegalStateException( "'" + valueName + "' does not exist!" );
                queryStack.push( value );
            }
        } );

        return queryStack.pop();
    }

    /**
     * Same as query() but returns a boolean.
     *
     * @param query The query to make.
     * @return true or false.
     * @exception IllegalStateException on reference of non existent value.
     */
    public boolean queryAsBool( String query ) throws IllegalStateException {

        return query( query ).equals( "T" );
    }
}
