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
import groovy.transform.PackageScope
import se.natusoft.tools.modelish.Plugin
import se.natusoft.tools.modelish.internal.ModelishInvocationHandler

import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * This is the API for using Modelish. It contains only static methods.
 *
 * @param <T>  A model API somewhere extending Model.
 */
@CompileStatic
class Modelish<ModelType> {

    /** Plugins to use by ModelishInvocationHandler. */
    private static List<Plugin> plugins = [ ]

    /**
     * Adds a plugin to be executed when models are created.
     *
     * Warning: Once you have installed a plugin, it cannot be uninstalled!!
     * This behavior is intentional. If it is a good idea remains to be seen ...
     */
    static installPlugin(Plugin plugin) {
        plugins << plugin
    }

    /**
     * ModelishInvocationHandler will call this.
     */
    static List<Plugin> getPlugins() {
        plugins
    }

    /**
     * Provides a static method that creates an instance of the specified model interface class.
     *
     * Supports both Fluent (no set/get, only name, with or without argument for
     * setter / getter) or JavaBean standard.
     *
     * Modelish interfaces should extend the base "Model" interface.
     *
     * @param <Model>   The type of the model to create.
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

    // Delete when done!!! -->

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
    @PackageScope static Map<String, Object> modelToMap( Model model ) {

        Map<String, Object> modelAsMap = [ : ]

        model.class.interfaces.each { Class api ->

            api.methods.findAll { Method method ->

                method.parameterCount == 0 && method.returnType != null

            }.each { Method method ->

                        String prop = method.name

                        // In case this is using JavaBean standard.
                        if ( prop.startsWith( "get" ) ) {

                            prop = prop.substring( 3 )
                            prop = prop.substring( 0, 1 ).toLowerCase() + prop.substring( 1 )
                        }

                        Object value = method.invoke( model )

                        modelAsMap[ prop ] =
                            value instanceof Model ? modelToMap( value as Model ) : value as Object
                    }
        }

        modelAsMap
    }

    /**
     * Takes JSON data represented as a Map and invokes the appropriate setters of the created model
     * instance.
     *
     * @param map JSON data represented as a Map.
     * @param modelType An interface that in the end of the inheritance chain extends Model.
     *
     * @return A Modelish model instance.
     */
    @PackageScope static Model modelFromMap( Map<String, Object> map, Class<Model> modelType ) {

        Model model = create( modelType )

        modelType.interfaces.each { Class<?> api ->

            api.methods.find { Method method ->

                method.parameterCount == 1 && !method.name.startsWith( "_" )
            }
                    .each { Method method ->

                        String prop = method.name

                        System.err.println ">>>> ${prop}"

                        // In case this is using JavaBean standard.
                        if ( prop.startsWith( "get" ) ) {

                            prop = prop.substring( 3 )
                            prop = prop.substring( 0, 1 ).toLowerCase() + prop.substring( 1 )
                        }

                        Object value = map[ prop ]

                        if ( value instanceof Map ) {
                            method.invoke( modelFromMap( value as Map, api as Class<Model> ) )
                        }
                        else {
                            method.invoke( model, value )
                        }
                    }
        }

        model
    }

    /**
     * Writes the specified Modelish model as JSON to the specified OutputStream.
     */
    static void writeJSON( Model model, OutputStream jsonStream ) {
        JSON.std.write( modelToMap( model ), jsonStream )
    }

    /**
     * Reads JSON from the specified InputStream and returns a Model instance of it.
     *
     * @param jsonStream A stream containing JSON data.
     * @param model The Modelsih type to return populated with the JSON data.
     *
     * @return A populated Model subclass. You need to cast it to what it really is,
     *         i.e the class represented by the 'model' argument.
     */
    static Model readJSON( InputStream jsonStream, Class model ) {
        Map<String, Object> jsonMap = JSON.std.mapFrom( jsonStream )
        modelFromMap( jsonMap, model )
    }
}

// Try to shoot in backing Modelish Map in proxied instance.
