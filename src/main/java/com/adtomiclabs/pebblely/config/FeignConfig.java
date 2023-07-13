package com.adtomiclabs.pebblely.config;

import feign.Logger;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;

import java.time.format.DateTimeFormatter;

/**
 * The {@code FeignConfig} class is a configuration class for Feign clients in the Pebblely application.
 * It provides configuration for request interceptors, logging level, and custom formatters.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class FeignConfig {

    /**
     * The API key for accessing the Pebblely API.
     */
    @Value("${pebblely.api-key}")
    private String pebblelyAPiKey;

    /**
     * The formatter for date values.
     */
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * The formatter for time values.
     */
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * The formatter for date-time values.
     */
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Creates a request interceptor that adds the Pebblely API key to the request header.
     *
     * @return the request interceptor
     */
    @Bean
    public RequestInterceptor requestApiKeyInterceptor() {
        return requestTemplate -> requestTemplate.header("X-Pebblely-Access-Token", pebblelyAPiKey);
    }

    /**
     * Specifies the logging level for Feign clients.
     *
     * @return the logging level
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * Registers custom formatters for date, time, and date-time values.
     *
     * @return the Feign formatter registrar
     */
    @Bean
    public FeignFormatterRegistrar feignFormatterRegistrar() {
        return formatterRegistry -> {
            DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
            registrar.setUseIsoFormat(false);
            registrar.setDateFormatter(dateFormatter);
            registrar.setTimeFormatter(timeFormatter);
            registrar.setDateTimeFormatter(dateTimeFormatter);
            registrar.registerFormatters(formatterRegistry);
        };
    }

}

