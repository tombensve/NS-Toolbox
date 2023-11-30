package se.natusoft.toolbox.annotatedapi2md

import se.natusoft.toolbox.annotatedapi2md.annotations.Method
import se.natusoft.toolbox.annotatedapi2md.annotations.Param
import se.natusoft.toolbox.annotatedapi2md.annotations.Type

@Type(
        name="AnnotatedTestClass",
        desc=[
                "This describes",
                "the AnnotatedTestClass"
        ]
)
class AnnotatedTestClass {

    @Method(
            name="at1",
            desc=[
                    "Some",
                    "description"
            ]
    )
    AnnotatedTestClass(String a, int b) {

    }

    int someMethod(
            @Param(name="qaz", desc="Tjohej") String qaz
    ) {

    }
}