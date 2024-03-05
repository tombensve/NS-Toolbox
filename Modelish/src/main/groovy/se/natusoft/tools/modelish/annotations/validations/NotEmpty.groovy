package se.natusoft.tools.modelish.annotations.validations

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * For string values that should not be empty nor only contain spaces.
 */
@Retention( RetentionPolicy.RUNTIME)
@Target( ElementType.METHOD)
@interface NotEmpty {}
