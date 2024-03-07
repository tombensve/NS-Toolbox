package se.natusoft.tools.modelish.validators

import se.natusoft.tools.modelish.ModelishException
import se.natusoft.tools.modelish.ModelishValidator

import java.lang.annotation.Annotation
import java.lang.reflect.Method

/**
 * Validates that model value is not null.
 */
class NoNullValidator implements ModelishValidator {

    /**
     * Validates a model value.
     *
     * @param ann a possible validation annotation to check.
     * @param args The arguments to validate.
     * @param method The method being called.
     *
     * @throws ModelishException if not valid.
     */
    @Override
    void validate ( Annotation ann, Object[] args, Method method ) throws IllegalArgumentException {
        String name = ann.toString ().toLowerCase ()

        // NOTE: This does not care about annotation type, only that the name contains "no" and "null"!
        if ( name.contains ( "no" ) && name.contains ( "null" ) ) {
            if ( args [ 0 ] == null )
                throw new IllegalArgumentException ( "null passed to non nullable '${method.name}'!" )
        }
    }
}
