package com.adtomiclabs.pebblely.config;

import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for storage properties.
 */
@Configuration
public class StoragePropertiesConfig {

    private static final String LOCATION = "files";

    /**
     * Returns the folder location for storing Pebblely files.
     *
     * @return the Pebblely files directory location
     */
    public String getLocation() {
        return LOCATION;
    }

}
