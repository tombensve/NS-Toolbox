package se.natusoft.toolbox.annotatedapi2md.annotations

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
@interface ParamDoc {
    String name()
    String[] desc()
}