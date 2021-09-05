package se.natusoft.query;

import se.natusoft.query.api.QueryData;

import java.util.Properties;

public class PropertiesQueryData implements QueryData {

    private Properties dataMap;

    public PropertiesQueryData( Properties sourceMap) {
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
        return this.dataMap.getProperty(name);
    }
}
