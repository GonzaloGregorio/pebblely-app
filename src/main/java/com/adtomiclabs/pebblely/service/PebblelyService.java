package com.adtomiclabs.pebblely.service;

import com.adtomiclabs.pebblely.model.request.*;
import com.adtomiclabs.pebblely.model.response.PebblelyResponseDto;
import com.adtomiclabs.pebblely.exception.PebblelyException;
import com.adtomiclabs.pebblely.feign.PebblelyApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PebblelyService {

    private final PebblelyApi api;

    /**
     * Retrieves the available credits from the Pebblely API.
     *
     * @return the number of available credits
     * @throws PebblelyException if an error occurs while retrieving the credits
     */
    public int getCredits() throws PebblelyException {
        try {
            return api.getCredits().getCredits();
        } catch (Exception exception) {
            LOG.error("Pebblely API error - Credits. Error description: {}", exception.getMessage());
            throw new PebblelyException("Pebblely API error - Credits", exception);
        }
    }

    /**
     * Upscales an image using the Pebblely API.
     *
     * @param upscaleDto the DTO containing the upscale parameters
     * @return the response DTO from the API
     * @throws PebblelyException if an error occurs while upscaling the image
     */
    public PebblelyResponseDto upscale(UpscaleDto upscaleDto) throws PebblelyException {
        try {
            return api.upscale(upscaleDto);
        } catch (Exception exception) {
            LOG.error("Pebblely API error - Upscale. Error description: {}", exception.getMessage());
            throw new PebblelyException("Pebblely API error - Upscale", exception);
        }
    }

    /**
     * Removes the background from an image using the Pebblely API.
     *
     * @param imageDto the DTO containing the image parameters
     * @return the response DTO from the API
     * @throws PebblelyException if an error occurs while removing the background
     */
    public PebblelyResponseDto removeBackground(ImageDto imageDto) throws PebblelyException {
        try {
            return api.removeBackground(imageDto);
        } catch (Exception exception) {
            LOG.error("Pebblely API error - Remove Background. Error description: {}", exception.getMessage());
            throw new PebblelyException("Pebblely API error - Remove Background", exception);
        }
    }

    /**
     * Creates a background image using the Pebblely API.
     *
     * @param createBackgroundDto the DTO containing the background parameters
     * @return the response DTO from the API
     * @throws PebblelyException if an error occurs while creating the background
     */
    public PebblelyResponseDto createBackground(CreateBackgroundDto createBackgroundDto) throws PebblelyException {
        try {
            return api.createBackground(createBackgroundDto);
        } catch (Exception exception) {
            LOG.error("Pebblely API error - Create Background. Error description: {}", exception.getMessage());
            throw new PebblelyException("Pebblely API error - Create Background", exception);
        }
    }

    /**
     * Inpaints an image using the Pebblely API.
     *
     * @param inpaintDto the DTO containing the inpaint parameters
     * @return the response DTO from the API
     * @throws PebblelyException if an error occurs while inpainting the image
     */
    public PebblelyResponseDto inpaint(InpaintDto inpaintDto) throws PebblelyException {
        try {
            return api.inpaint(inpaintDto);
        } catch (Exception exception) {
            LOG.error("Pebblely API error - Inpaint. Error description: {}", exception.getMessage());
            throw new PebblelyException("Pebblely API error - Inpaint", exception);
        }
    }

    /**
     * Outpaints an image using the Pebblely API.
     *
     * @param outpaintDto the DTO containing the outpaint parameters
     * @return the response DTO from the API
     * @throws PebblelyException if an error occurs while outpainting the image
     */
    public PebblelyResponseDto outpaint(OutpaintDto outpaintDto) throws PebblelyException {
        try {
            return api.outpaint(outpaintDto);
        } catch (Exception exception) {
            LOG.error("Pebblely API error - Outpaint. Error description: {}", exception.getMessage());
            throw new PebblelyException("Pebblely API error - Outpaint", exception);
        }
    }

}
