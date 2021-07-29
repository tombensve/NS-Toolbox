package se.natusoft.rpnquery;

import java.util.Map;

public class MapQuery implements QueryData {

    private Map<String, String> dataMap;

    public MapQuery(Map<String, String> sourceMap) {
        this.dataMap = sourceMap;
    }

    /**
     * Should return data for the given name.
     *
     * @param name The named data to get.
     *
     * @return The value of the named data.
     */
    public String getByName(String name) {
        return this.dataMap.get(name);
    }
}
