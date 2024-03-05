package se.natusoft.tools.modelish.validators

import se.natusoft.tools.modelish.ModelishException
import se.natusoft.tools.modelish.ModelishValidator

import java.lang.annotation.Annotation

class NotEmptyValidator implements ModelishValidator {

    /**
     * Validates a model value.
     *
     * @param ann a possible validation annotation to check.
     * @param args The arguments to validate.
     * @param methodName The name of the method called.
     *
     * @throws ModelishException if not valid.
     */
    @Override
    void validate ( Annotation ann, Object[] args, String methodName ) throws ModelishException {

        String annName = ann.toString ().toLowerCase ()

        if ( annName.contains ( "no" ) &&
                annName.contains ( "empty" ) &&
                args [ 0 ] instanceof String &&
                ( args [ 0 ] as String ).trim ().size () == 0 ) {

            throw new ModelishException ( "Empty string not allowed here! '(${methodName})'" )
        }
    }
}
