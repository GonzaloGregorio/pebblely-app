package com.adtomiclabs.pebblely.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for Spring Web MVC.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * Adds CORS mappings to allow cross-origin requests from any origin.
     *
     * @param registry the CorsRegistry to configure CORS mappings
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    /**
     * Adds resource handlers for serving static resources.
     *
     * @param registry the ResourceHandlerRegistry to configure resource handlers
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

}
