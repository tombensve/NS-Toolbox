package se.natusoft.rpnquery;

/**
 * API for providing data to query.
 */
public interface QueryData {

    /**
     * Should return data for the given name.
     *
     * @param name The named data to get.
     *
     * @return The value of the named data.
     */
    String getByName(String name);
}
