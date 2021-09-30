/*
 *
 * PROJECT
 *     Name
 *         rpnquery
 *
 * COPYRIGHTS
 *     Copyright (C) 2021 by Natusoft AB All rights reserved.
 *
 * LICENSE
 *     Apache 2.0 (Open Source)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 * AUTHORS
 *     tommy ()
 *         Changes:
 *         2021-09-05: Created!
 *
 */
package se.natusoft.nvquery.data.providers;

import se.natusoft.nvquery.api.QueryData;

import java.util.Properties;

/**
 * Provides and implementation of QueryData using a java.util.Properties object.
 */
public class PropertiesQueryData implements QueryData {
    /** The provided properties to query. */
    private Properties properties;

    /**
     * Creates a new PropertiesQueryData.
     *
     * @param properties The Properties to query.
     */
    public PropertiesQueryData( Properties properties ) {
        this.properties = properties;
    }

    /**
     * Creates a new PropertiesQueryData with a default and empty set of properties.
     */
    public PropertiesQueryData() {
        this.properties = new Properties();
    }

    /**
     * Adds content to the properties.
     *
     * @param name The name of the content.
     * @param value The content value.
     * @return 'this' to provide builder pattern.
     */
    public PropertiesQueryData add( String name, Object value ) {
        this.properties.put( name, "" + value );

        return this;
    }

    /**
     * Should return data for the given name.
     *
     * @param name The named data to get.
     * @return The value of the named data.
     */
    public String getByName( String name ) {
        return this.properties.getProperty( name );
    }
}
