package com.adtomiclabs.pebblely.utils;

/**
 * Constants defining the endpoints for the Pebblely API.
 */
public class PebblelyEndpointConstants {

    /**
     * Private constructor to prevent instantiation of the utility class.
     */
    private PebblelyEndpointConstants() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * The base URL for the Pebblely API.
     */
    public static final String PEBBLELY_BASE_URL = "https://api.pebblely.com";

    /**
     * The endpoint for retrieving credits.
     */
    public static final String CREDITS_ENDPOINT = "/credits/v1";

    /**
     * The endpoint for upscaling images.
     */
    public static final String UPSCALE_ENDPOINT = "/upscale/v1";

    /**
     * The endpoint for removing backgrounds from images.
     */
    public static final String REMOVE_BACKGROUND_ENDPOINT = "/remove-background/v1";

    /**
     * The endpoint for creating backgrounds.
     */
    public static final String CREATE_BACKGROUND_ENDPOINT = "/create-background/v2";

    /**
     * The endpoint for inpainting images.
     */
    public static final String INPAINT_ENDPOINT = "/inpaint/v1";

    /**
     * The endpoint for outpainting images.
     */
    public static final String OUTPAINT_ENDPOINT = "/outpaint/v1";
}

