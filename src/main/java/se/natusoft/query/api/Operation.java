package se.natusoft.query.api;

/**
 * Defines a generic operation. The last 2 entries on stack will be passed as value1 and value2.
 */
public interface Operation {

    /**
     * Executes the operation on the 2 provided values.
     *
     * @param value1 First value.
     * @param value2 Second value.
     *
     * @return true or false.
     */
    boolean execute(String value1, String value2);
}
