package se.natusoft.query;

/**
 * Represents a query that always returns a boolean.
 */
public interface BooleanQuery {

    /**
     * Makes a query.
     *
     * @param query The query string to execute.
     *
     * @return true or false.
     */
    boolean query(String query);
}
