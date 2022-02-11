package se.natusoft.tools.modelish;

import se.natusoft.tools.modelish.internal.ModelishInvocationHandler;

import java.lang.reflect.Proxy;

public class Modelish<T> {

    public static <Model> Model create(Class<Model> api) {
        Class<?>[] interfaces = new Class[ 1 ];
        interfaces[ 0 ] = api;

        //noinspection unchecked
        return ( Model ) Proxy.newProxyInstance( api.getClassLoader(), interfaces, new ModelishInvocationHandler() );

    }
}
