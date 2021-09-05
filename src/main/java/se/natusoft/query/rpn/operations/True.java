package se.natusoft.query.rpn.operations;

import se.natusoft.query.api.Operation;

public class True implements Operation {

    /**
     * Executes the operation on the 2 provided values.
     *
     * @param value1 First value.
     * @param value2 Second value.
     * @return true or false.
     */
    @Override
    public boolean execute( String value1, String value2 ) {
        return value1.equals( "T" ) && value2.equals( "T" );
    }

    /**
     * Checks if a string value represents a true value.
     *
     * @param value The string value to check.
     *
     * @return true or false.
     */
    public static boolean is(String value) {
        return value.trim().equals( "T" );
    }
}
