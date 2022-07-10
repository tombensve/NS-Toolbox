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

import com.fasterxml.jackson.jr.ob.JSON
import groovy.transform.CompileStatic
import se.natusoft.tools.modelish.internal.ModelishInvocationHandler

import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * This is the API for using Modelish. It contains only static methods.
 *
 * @param <T> A model API somewhere extending Model.
 */
@CompileStatic
class Modelish<T> {

    /**
     * Provides a static method that creates an instance of the specified model interface class.
     *
     * Rules are fluent API, that is same name for getter and setter where
     * the getter has no arguments and the setter one argument, or getter/setter, both are
     * supported.
     *
     * Modelish interfaces should extend the base "Model" interface.
     *
     * @param <Model>  The type of the model to create.
     */
    static <Model> Model create( Class<Model> api ) {
        Class<?>[] interfaces = new Class[1]
        interfaces[ 0 ] = api

        //noinspection unchecked
        return (Model) Proxy.newProxyInstance(
                api.getClassLoader(),
                interfaces,
                new ModelishInvocationHandler()
        )

    }

    // Support for producing JSON from Modelish model and reading JSON into a
    // Modelish Model. Jackson Jr is used for this.
    //
    // Do note that Jackson Jr can work with JSON in Map format, and that Modelish
    // is handling all values by using a Map. I did spend a lot of time to figure
    // out a way to make use of that, but finally came to the conclusion that I
    // should completely forget about it. The values of Modelish sub models are
    // instantiated proxies. It is the proxy that sits on the Map. It gets really
    // messy, especially for writing. I'm making this note to remind myself that
    // I have really thought this through and decided it would be too messy. The
    // fact that it currently have an internal Map is an internal solution that
    // should not be considered outside of instance, which is true for all such
    // situations, not just this. So I was wrong already when I started thinking
    // about it.
    //
    // Also note that I'm using Jackson Jr rather than Groovys JsonSlurper due
    // to a warning from the Groovy users list that JsonSluper has a bug in hanling
    // lists.

    /**
     * Takes a Model with potential sub Model instances and returns a Map
     * that Jackson Jr can translate to JSON.
     *
     * @param model The Modelish model to translate to a Map<String, Object>.
     */
    private static Map<String, Object> modelToMapModel( Model model ) {

        Map<String, Object> mapModel = [ : ]

        model.getClass().getMethods().find { Method method ->
            // Might need more filtering than this!
            method.parameterCount == 0
        }.each { Method method ->

            String prop = method.name
            if ( prop.startsWith( "get" ) ) {

                prop = prop.substring( 3 )
                prop = prop.substring( 0, 1 ).toLowerCase() + prop.substring( 1 )
            }

            Object value = method.invoke( model )

            if ( value instanceof Model ) {
                value = modelToMapModel( value as Model )
            }
            else {
                mapModel.put( prop, value )
            }
        }

        mapModel
    }

    /**
     *
     */
    static void writeJSON( OutputStream jsonStream, Model model ) {
        JSON.std.write( modelToMapModel( model ), jsonStream );
    }

    static <Model> Model readJSON( Class<Model> api , InputStream jsonStream) {

    }
}
