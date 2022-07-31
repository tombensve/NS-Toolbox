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
import se.natusoft.tools.modelish.internal.ModelishInvocationHandler

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

    static <Model> Model create( Class<Model> api ) {
        Class<?>[] interfaces = new Class[1]
        interfaces[ 0 ] = api

        //noinspection unchecked
        return (Model) Proxy.newProxyInstance( api.getClassLoader(), interfaces,
                new ModelishInvocationHandler(api as Class<se.natusoft.tools.modelish.Model>) )
        // For some reason that I'm not getting why the above class type needs a fully qualified
        // package! There is probably a wrong declaration somewhere that does not fail build,
        // and that I'm failing to see.
    }
}
