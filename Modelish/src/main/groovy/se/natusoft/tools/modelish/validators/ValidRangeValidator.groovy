package se.natusoft.tools.modelish.validators

import se.natusoft.tools.modelish.ModelishException
import se.natusoft.tools.modelish.ModelishValidator
import se.natusoft.tools.modelish.annotations.validations.ValidRange

import java.lang.annotation.Annotation
import java.lang.reflect.Method

/**
 * Validates the range of numeric values, excluding BigDecimal!
 */
class ValidRangeValidator implements ModelishValidator {

    /**
     * Validates a range of value.
     *
     * @param ann a possible validation annotation to check.
     * @param args The arguments to validate.
     * @param method The method called.
     *
     * @throws se.natusoft.tools.modelish.ModelishException if not valid.
     */
    @Override
    void validate ( Annotation ann, Object[] args, Method method ) throws ModelishException {

        //
        String annName = ann.toString ().toLowerCase ()

        if ( annName.contains ( "valid" ) && annName.contains ( "range" ) ) {

            ValidRange validRange = ann as ValidRange

            double value = args [ 0 ] as double
            if ( value < validRange.min () || value > validRange.max () )
                throw new ModelishException ( "Value out of range! [${validRange.min ()} - ${validRange.max ()}]" )
        }

    }

}
