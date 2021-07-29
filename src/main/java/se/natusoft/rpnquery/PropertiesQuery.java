package se.natusoft.rpnquery;

import java.util.Properties;

public class PropertiesQuery implements QueryData {

    private Properties dataMap;

    public PropertiesQuery( Properties sourceMap) {
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
