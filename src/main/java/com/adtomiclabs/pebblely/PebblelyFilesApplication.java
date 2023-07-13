package com.adtomiclabs.pebblely;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * The main entry point for the Pebblely Files Application.
 */
@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.adtomiclabs.pebblely")
public class PebblelyFilesApplication {

    /**
     * The main method that starts the Pebblely Files Application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(PebblelyFilesApplication.class, args);
    }

}
