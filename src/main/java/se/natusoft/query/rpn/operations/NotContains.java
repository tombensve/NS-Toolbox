package se.natusoft.query.rpn.operations;

import se.natusoft.query.api.Operation;

public class NotContains extends Contains implements Operation {

    /**
     * Executes the operation on the 2 provided values.
     *
     * @param value1 First value.
     * @param value2 Second value.
     * @return true or false.
     */
    @Override
    public boolean execute( String value1, String value2 ) {
        return !super.execute( value1, value2 );
    }
}
