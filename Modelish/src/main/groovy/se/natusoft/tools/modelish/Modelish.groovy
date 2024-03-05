/*
 *
 * PROJECT
 *     Name
 *         Modelish
 *
 *     Description
 *         Provides a RPN Query against name value set of data (Properties, Map).
 *
 * COPYRIGHTS
 *     Copyright (C) 2022 by Natusoft AB All rights reserved.
 *
 * LICENSE
 *     Apache 2.0 (Open Source)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 * AUTHORS
 *     tommy ()
 *         Changes:
 *         2022-02-11: Created!
 *
 */
package se.natusoft.tools.modelish

import groovy.transform.CompileStatic
import se.natusoft.tools.modelish.annotations.validations.NoNull
import se.natusoft.tools.modelish.internal.Internal
import se.natusoft.tools.modelish.internal.ModelishInvocationHandler

import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * Provides a static method that creates an instance of the specified model interface class
 *
 * Rules are fluent API, that is same name for getter and setter where the getter has no arguments
 * and the setter one argument.
 *
 * Model interfaces should extend the ModelishModel interface.
 *
 * @param <T>
 */
@CompileStatic
class Modelish<T> {

    /**
     * Creates a new empty model.
     *
     * @param api The model API to create.
     *
     * @return A new model instance.
     */
    static <Model> Model create( Class<Model> api ) {

        Class<?>[] interfaces = [ api, Internal.class ]

        //noinspection unchecked
        return (Model) Proxy.newProxyInstance( api.getClassLoader(), interfaces, new ModelishInvocationHandler() )

    }

    /**
     * Creates a new Model from a Map structure.
     *
     * @param map The Map to be converted into a model.
     * @param model The type of model to create.
     *
     * @return a new model instance.
     */
    static Model<Model> createFromMap( Class<Model> api, Map<String, Object> map ) {

        validate( api, map )

        Model<Model> model = create( api )
        ( model as Internal )._provideMap( map )

        model
    }

    /**
     * Validates the passed Map against method annotations.
     *
     * @param api The model API possibly containing @NotNull annotations.
     * @param map The input Map to validate.
     */
    private static void validate( Class api, Map<String, Object> map ) {

        List<Method> propMethods = [ ]

        Class vApi = api
        while ( vApi != null ) {

            propMethods.addAll( api.methods )
            vApi = vApi.superclass
        }

        propMethods.each { Method m ->

            String propName

            if ( m.parameterCount == 1 ) {

                // Check not null

                NoNull noNull = m.getAnnotation( NoNull.class )
                if ( noNull != null ) {

                    propName = m.name

                    if (propName.startsWith( "get" ) || propName.startsWith( "set" )) {
                        propName = propName.substring( 3 )
                        propName = propName.substring( 0, 1 ).toLowerCase() + propName.substring( 1 )
                    }

                    if ( map[ propName ] == null ) {
                        throw new IllegalArgumentException( "Property '${propName}' cannot be null!" )
                    }
                }

                // Without adding more specific model annotations there is nothing more we can check.

            }
        }

    }
}
