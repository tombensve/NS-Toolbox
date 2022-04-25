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
 *     Licensed under the Apache License, Version 2.0 (the "License")
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
package se.natusoft.tools.modelish.internal

import groovy.transform.CompileStatic
import se.natusoft.tools.modelish.Cloneable
import se.natusoft.tools.modelish.ModelishException
import se.natusoft.tools.modelish.Model
import se.natusoft.tools.modelish.NoNull

import java.lang.annotation.Annotation
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * This provides an implementation to a model API, using java.lang.reflect.Proxy.
 */
@CompileStatic
class ModelishInvocationHandler implements InvocationHandler {

    /** This holds the models values. */
    private Map<String, Object> values = [ : ]

    /** When this is set to true the contents of a model can no longer be changed. */
    private boolean locked = false

    /** Default handler */
    ModelishInvocationHandler() {}

    /**
     * Used when cloning.
     *
     * @param copy a copy of the model data which is used instead of a new empty.
     */
    private ModelishInvocationHandler( Map<String, Object> copy ) {
        this.values = copy
    }

    // Kept the IDEA generated javadoc copy for invoke(...).
    /**
     * Processes a method invocation on a proxy instance and returns
     * the result.  This method will be invoked on an invocation handler
     * when a method is invoked on a proxy instance that it is
     * associated with.
     *
     * @param proxy the proxy instance that the method was invoked on
     * @param method the {@code Method} instance corresponding to
     *               the interface method invoked on the proxy instance.  The declaring
     *               class of the {@code Method} object will be the interface that
     *               the method was declared in, which may be a superinterface of the
     *               proxy interface that the proxy class inherits the method through.
     * @param args an array of objects containing the values of the
     *               arguments passed in the method invocation on the proxy instance,
     *               or {@code null} if interface method takes no arguments.
     *               Arguments of primitive types are wrapped in instances of the
     *               appropriate primitive wrapper class, such as
     * {@code java.lang.Integer} or {@code java.lang.Boolean}.
     *
     * @return the value to return from the method invocation on the
     * proxy instance.  If the declared return type of the interface
     * method is a primitive type, then the value returned by
     * this method must be an instance of the corresponding primitive
     * wrapper class otherwise, it must be a type assignable to the
     * declared return type.  If the value returned by this method is
     * {@code null} and the interface method's return type is
     * primitive, then a {@code NullPointerException} will be
     * thrown by the method invocation on the proxy instance.  If the
     * value returned by this method is otherwise not compatible with
     * the interface method's declared return type as described above,
     * a {@code ClassCastException} will be thrown by the method
     * invocation on the proxy instance.
     *
     * @throws Throwable the exception to throw from the method
     *                   invocation on the proxy instance.  The exception's type must be
     *                   assignable either to any of the exception types declared in the
     * {@code throws} clause of the interface method or to the
     *                   unchecked exception types {@code java.lang.RuntimeException}
     *                   or {@code java.lang.Error}.  If a checked exception is
     *                   thrown by this method that is not assignable to any of the
     *                   exception types declared in the {@code throws} clause of
     *                   the interface method, then an
     * {@link UndeclaredThrowableException} containing the
     *                   exception that was thrown by this method will be thrown by the
     *                   method invocation on the proxy instance.
     * @see UndeclaredThrowableException
     */
    @SuppressWarnings( 'GroovyDocCheck' )
    @Override
    Object invoke( Object proxy, Method method, Object[] args ) throws Throwable {

        Object result = proxy

        // Also handle Java Bean get/set methods.
        String calledMethod = method.name
        if (calledMethod.startsWith( "get" ) || calledMethod.startsWith( "set" )) {
            calledMethod = calledMethod.substring( 3 )
            calledMethod = calledMethod.substring( 0, 1 ).toLowerCase() + calledMethod.substring( 1 )
        }

        //noinspection GroovyFallthrough
        switch ( calledMethod ) {

            case "_lock":
                this.locked = true
                break

            case "_recursiveLock":
                this.locked = true

                // Also lock any sub models.
                for ( String key : this.values.keySet() ) {
                    Object value = this.values.get( key )
                    if ( value instanceof Model ) {
                        ( (Model<?>) value )._recursiveLock()
                    }
                }
                break

            case "_clone":
            case "_create":
                result = doClone( proxy.getClass().getInterfaces()[ 0 ], this.values )
                break

            default:

                // Validation of bad model interface. Can only be 0 or 1 argument.
                if ( args != null && args.length > 1 ) {
                    throw new ModelishException(
                            "Badly defined model method! Can only be one value for setter."
                    )
                }

                // Setter
                if ( args != null && args.length == 1 ) {

                    // Validate nullability of setter.
                    Annotation noNull = method.getAnnotation( NoNull.class )
                    if ( noNull != null && args[ 0 ] == null ) {
                        throw new ModelishException( "null passed to non nullable '${method.name}'!" )
                    }

                    if ( this.locked ) {
                        throw new ModelishException( "Update of read only object not allowed!" )
                    }

                    this.values.put( calledMethod, args[ 0 ] )

                }
                // Getter
                else if ( args == null ) {

                    result = this.values.get( calledMethod )

                }
                // Bad model!
                else {
                    throw new ModelishException(
                            "Bad model API! Only one (setter) or zero (getter) argument are valid."
                    )
                }
        }

        return result

    }

    /**
     * Clones a model returning a complete copy of source model.
     *
     * Note that any value that is a `CloneableModelishModel` is also cloned!
     *
     * @param api The model API.
     * @param source The source model being cloned.
     *
     * @return a new Proxy implementation of the clone.
     */
    private static Object doClone( Class<?> api, Map<String, Object> source ) {

        Map<String, Object> copy = new LinkedHashMap<>()

        for ( String key : source.keySet() ) {

            Object srcObj = source.get( key )

            if ( srcObj instanceof Cloneable ) {

                srcObj = ( (Cloneable<?>) srcObj )._clone()
            }

            copy.put( key, srcObj )
        }

        Class<?>[] interfaces = new Class[1]
        interfaces[ 0 ] = api

        return Proxy.newProxyInstance( api.getClassLoader(), interfaces, new ModelishInvocationHandler( copy ) )
    }
}
