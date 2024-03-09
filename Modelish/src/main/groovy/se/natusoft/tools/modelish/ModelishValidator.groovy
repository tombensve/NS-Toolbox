package se.natusoft.tools.modelish

import se.natusoft.tools.modelish.validators.NoNullValidator
import se.natusoft.tools.modelish.validators.NotEmptyValidator
import se.natusoft.tools.modelish.validators.ValidRangeValidator

import java.lang.annotation.Annotation
import java.lang.reflect.Method

/**
 * Implementations of this can be passed to Modelish.
 */
interface ModelishValidator {

    /**
     * This sets upp default validators, but it is possible to implement and add
     * any more validators you want! It of course must be done before Modelish is used!
     *
     * I provide no API for this, but this is a public LinkedList so you can remove
     * specific validators if you want! I would however caution against it for
     * obvious reasons! If you want to turn validations on or off, provide that
     * functionality in the validator!
     */
    static final List<ModelishValidator> VALIDATORS =
            new LinkedList<ModelishValidator> (
                    // Default validators! Other validators can be added to this list.
                    [
                            new NoNullValidator (),
                            new NotEmptyValidator (),
                            new ValidRangeValidator ()
                    ]
            )

    /**
     * Validates a model value.
     *
     * @param ann a possible validation annotation to check.
     * @param args The arguments to validate.
     * @param methodName The name of the method called.
     *
     * @throws ModelishException if not valid.
     */
    void validate ( Annotation ann, Object[] args, Method method ) throws ModelishException
}
