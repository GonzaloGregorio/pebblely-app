package com.adtomiclabs.pebblely.feign;

import com.adtomiclabs.pebblely.config.FeignConfig;
import com.adtomiclabs.pebblely.model.request.*;
import com.adtomiclabs.pebblely.model.response.PebblelyResponseDto;
import com.adtomiclabs.pebblely.utils.PebblelyEndpointConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Feign client interface for interacting with the Pebblely API.
 */
@FeignClient(value = "PebblelyApi", url = PebblelyEndpointConstants.PEBBLELY_BASE_URL, configuration = FeignConfig.class)
public interface PebblelyApi {

    /**
     * Retrieves the credits information from the Pebblely API.
     *
     * @return The response containing the credits' information.
     */
    @GetMapping(value = PebblelyEndpointConstants.CREDITS_ENDPOINT, consumes = MediaType.APPLICATION_JSON_VALUE)
    PebblelyResponseDto getCredits();

    /**
     * Upscales an image using the Pebblely API.
     *
     * @param upscaleDto The request DTO containing the image and upscale parameters.
     * @return The response containing the upscaled image data.
     */
    @PostMapping(value = PebblelyEndpointConstants.UPSCALE_ENDPOINT, consumes = MediaType.APPLICATION_JSON_VALUE)
    PebblelyResponseDto upscale(@RequestBody UpscaleDto upscaleDto);

    /**
     * Removes the background from an image using the Pebblely API.
     *
     * @param imageDto The request DTO containing the image data.
     * @return The response containing the image with the background removed.
     */
    @PostMapping(value = PebblelyEndpointConstants.REMOVE_BACKGROUND_ENDPOINT, consumes = MediaType.APPLICATION_JSON_VALUE)
    PebblelyResponseDto removeBackground(@RequestBody ImageDto imageDto);

    /**
     * Creates a background for an image using the Pebblely API.
     *
     * @param createBackgroundDto The request DTO containing the image and background creation parameters.
     * @return The response containing the image with the created background.
     */
    @PostMapping(value = PebblelyEndpointConstants.CREATE_BACKGROUND_ENDPOINT, consumes = MediaType.APPLICATION_JSON_VALUE)
    PebblelyResponseDto createBackground(@RequestBody CreateBackgroundDto createBackgroundDto);

    /**
     * Inpaints an image using the Pebblely API.
     *
     * @param inpaintDto The request DTO containing the image and inpainting parameters.
     * @return The response containing the inpainted image data.
     */
    @PostMapping(value = PebblelyEndpointConstants.INPAINT_ENDPOINT, consumes = MediaType.APPLICATION_JSON_VALUE)
    PebblelyResponseDto inpaint(@RequestBody InpaintDto inpaintDto);

    /**
     * Outpaints an image using the Pebblely API.
     *
     * @param outpaintDto The request DTO containing the image and outpainting parameters.
     * @return The response containing the outpainted image data.
     */
    @PostMapping(value = PebblelyEndpointConstants.OUTPAINT_ENDPOINT, consumes = MediaType.APPLICATION_JSON_VALUE)
    PebblelyResponseDto outpaint(@RequestBody OutpaintDto outpaintDto);

}

