package se.natusoft.tools.modelish

/**
 * This represents JSON data in Map format. Both Groovys JSONSlurper and JackssonJr can handle turning
 * this into JSON, and create Map from JSON. Thereby this does not lock to a specific JSON library.
 *
 * @param <T>
 */
interface JSON<T> extends Model<T> {

    Map<String, Object> jsonishMap()

    void jsonishMap(Map<String, Object> jsonishMap)
}
