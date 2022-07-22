package se.natusoft.tools.modelish.extensions

import se.natusoft.tools.modelish.Model
import se.natusoft.tools.modelish.Plugin

import java.lang.reflect.Method

class JSONPlugin implements Plugin {

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
    @Override
    Object handle( Map<String, Object> values, Object proxy, Method method, Object[] args, Object result )
            throws Throwable {

        if ( method.name == "_jsonishMap" ) {
            if (method.parameterCount == 0) {
                result = toMap( values )
            }
            else if ( method.parameterCount == 1) {

            }
        }


        return result
    }

    private Map<String, Object> toMap( Map<String, Object> values ) {

        Map<String, Object> result = [ : ]

        values.each { Map.Entry<String, Object> entry ->

            if (entry.value instanceof Model) {
                result[entry.key] = (entry as Model).__modelInternalData(  )
            }
            else {
                result[entry.key] = entry.value
            }
        }

        result
    }

    private Map<String, Object> toModelishInstance(Map<String, Object> values) {

        Map<String, Object> result = [ : ]

        values.each { Map.Entry<String, Object> entry ->

            if (entry.value instanceof Map) {

            }
        }
    }
}
