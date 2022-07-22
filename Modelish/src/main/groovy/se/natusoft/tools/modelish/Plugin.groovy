package se.natusoft.tools.modelish

import java.lang.reflect.Method

/**
 * A handler plugin.
 */
interface Plugin {

    /**
     * Same API as InvocationHandler, with extensions, and ModelishInvocationHandler will pass on to this.
     *
     * @Param values The values managed by ModelishInvocationHandler.
     * @param proxy See InvocationHandler JavaDoc or look in ModelishInvocationHandler.
     * @param method See InvocationHandler JavaDoc or look in ModelishInvocationHandler.
     * @param args See InvocationHandler JavaDoc or look in ModelishInvocationHandler.
     * @return A possibly modified result.
     *
     * @throws Throwable
     */
    Object handle(Map<String, Object> values,  Object proxy, Method method, Object[] args, Object result )
            throws Throwable

}
