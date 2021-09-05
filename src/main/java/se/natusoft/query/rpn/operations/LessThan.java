package se.natusoft.query.rpn.operations;

import se.natusoft.query.api.Operation;


public class LessThan implements Operation {

    /**
     * Executes the operation on the 2 provided values.
     *
     * @param value1 First value.
     * @param value2 Second value.
     * @return true or false.
     */
    @SuppressWarnings( "DuplicatedCode" )
    @Override
    public boolean execute( String value1, String value2 ) {
        boolean res;


        if (value1.contains( "." ) || value2.contains( "." ))
        {
            double v1 = Double.parseDouble( value1 );
            double v2 = Double.parseDouble( value2 );
            res = v1 < v2;
        }
        else {
            long v1 = Long.parseLong( value1 );
            long v2 = Long.parseLong( value2 );
            res = v1 < v2;
        }

        return res;
    }
}
