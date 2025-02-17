package se.natusoft.lic.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Retention( RetentionPolicy.RUNTIME)

/**
 * This annotation points out where
 */
@interface SourceAvailableAt {

    String value() default "https://github.com/tombensve/"
}
