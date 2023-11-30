package se.natusoft.toolbox.annotatedapi2md.annotations

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention( RetentionPolicy.SOURCE)
@Target( ElementType.TYPE)
@interface TypeDoc {
    String name()
    String[] desc()
}
