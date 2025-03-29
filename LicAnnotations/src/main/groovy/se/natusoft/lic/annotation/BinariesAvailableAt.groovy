package se.natusoft.lic.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Retention( RetentionPolicy.RUNTIME)

/**
 * This annotation points out where binaries are available.
 */
@interface BinariesAvailableAt {

    String value() default "https://repo.repsy.io/mvn/tombensve/natusoft-os/"
}
