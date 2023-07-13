package com.adtomiclabs.pebblely.service;

import com.adtomiclabs.pebblely.model.request.*;
import com.adtomiclabs.pebblely.model.response.PebblelyResponseDto;
import com.adtomiclabs.pebblely.exception.PebblelyException;
import com.adtomiclabs.pebblely.utils.FilesDirectoriesEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Service class for file processing operations.
 */
@AllArgsConstructor
@Service
public class FileProcessingService {

    private final PebblelyService pebblelyService;
    private final FileStorageService fileStorageService;

    /**
     * Upscales multiple images.
     *
     * @param multipartFiles The list of MultipartFile objects representing the images to upscale.
     * @param size           The upscale size.
     * @throws IOException       If an I/O error occurs during the image processing or storage.
     * @throws PebblelyException If an error occurs during the upscale operation.
     */
    public void upscaleImages(List<MultipartFile> multipartFiles, int size) throws IOException, PebblelyException {
        for (MultipartFile multipartFile : multipartFiles) {
            upscaleImage(multipartFile, size);
        }
    }

    /**
     * Removes the background from multiple images.
     *
     * @param multipartFiles The list of MultipartFile objects representing the images to process.
     * @throws IOException       If an I/O error occurs during the image processing or storage.
     * @throws PebblelyException If an error occurs during the background removal operation.
     */
    public void removeBackgrounds(List<MultipartFile> multipartFiles) throws IOException, PebblelyException {
        for (MultipartFile multipartFile : multipartFiles) {
            removeBackground(multipartFile);
        }
    }

    /**
     * Creates backgrounds from multiple images using the specified CreateBackgroundDto.
     *
     * @param multipartFiles      The list of MultipartFile objects representing the images to process.
     * @param createBackgroundDto The CreateBackgroundDto object containing the background creation parameters.
     * @throws IOException       If an I/O error occurs during the image processing or storage.
     * @throws PebblelyException If an error occurs during the background creation operation.
     */
    public void createBackgrounds(List<MultipartFile> multipartFiles, CreateBackgroundDto createBackgroundDto) throws IOException, PebblelyException {
        for (MultipartFile multipartFile : multipartFiles) {
            createBackground(multipartFile, createBackgroundDto);
        }
    }

    /**
     * Inpaints multiple images using the specified InpaintDto.
     *
     * @param multipartFiles The list of MultipartFile objects representing the images to process.
     * @param inpaintDto     The InpaintDto object containing the inpainting parameters.
     * @throws IOException       If an I/O error occurs during the image processing or storage.
     * @throws PebblelyException If an error occurs during the inpainting operation.
     */
    public void inpaintFiles(List<MultipartFile> multipartFiles, InpaintDto inpaintDto) throws IOException, PebblelyException {
        for (MultipartFile multipartFile : multipartFiles) {
            inpaintFile(multipartFile, inpaintDto);
        }
    }

    /**
     * Builds a CreateBackgroundDto object using the provided parameters.
     *
     * @param theme       The background theme template from Pebblely.
     * @param description The background description.
     * @param styleColor  The background style color.
     * @param styleImage  The background style image.
     * @param negative    The negative value.
     * @param height      The background height.
     * @param width       The background width.
     * @return The created CreateBackgroundDto object.
     */
    public CreateBackgroundDto buildCreateBackgroundDto(String theme, String description, String styleColor, MultipartFile styleImage, String negative, Integer height, Integer width) throws IOException {
        CreateBackgroundDto dto = new CreateBackgroundDto();
        if (Objects.nonNull(height)) {
            dto.setHeight(height);
        }
        if (Objects.nonNull(width)) {
            dto.setWidth(width);
        }
        buildBackgroundDto(dto, theme, description, styleColor, styleImage, negative);
        return dto;
    }

    /**
     * Builds an InpaintDto object using the provided parameters.
     *
     * @param mask        The mask value.
     * @param theme       The background theme template from Pebblely.
     * @param description The background description.
     * @param styleColor  The background style color.
     * @param styleImage  The background style image.
     * @param negative    The negative value.
     * @return The created InpaintDto object.
     */
    public InpaintDto buildInpaintDto(MultipartFile mask, String theme, String description, String styleColor, MultipartFile styleImage, String negative) throws IOException {
        String imageBase64 = fileStorageService.getMultipartFileInBase64(mask, FilesDirectoriesEnum.ORIGINALS.name());
        InpaintDto dto = new InpaintDto(imageBase64);
        return buildBackgroundDto(dto, theme, description, styleColor, styleImage, negative);
    }

    private void upscaleImage(MultipartFile multipartFile, int size) throws IOException, PebblelyException {
        String imageBase64 = fileStorageService.getMultipartFileInBase64(multipartFile, FilesDirectoriesEnum.ORIGINALS.name());
        UpscaleDto upscaleDto = UpscaleDto.builder()
                .image(imageBase64)
                .size(size)
                .build();
        PebblelyResponseDto result = pebblelyService.upscale(upscaleDto);
        fileStorageService.saveFileFromBase64(result.getData(), FilesDirectoriesEnum.UPSCALE.name(), multipartFile.getOriginalFilename());
    }

    private void removeBackground(MultipartFile multipartFile) throws IOException, PebblelyException {
        String imageBase64 = fileStorageService.getMultipartFileInBase64(multipartFile, FilesDirectoriesEnum.ORIGINALS.name());
        PebblelyResponseDto result = pebblelyService.removeBackground(new ImageDto(imageBase64));
        fileStorageService.saveFileFromBase64(result.getData(), FilesDirectoriesEnum.REMOVED.name(), multipartFile.getOriginalFilename());
    }

    private void createBackground(MultipartFile multipartFile, CreateBackgroundDto createBackgroundDto) throws IOException, PebblelyException {
        String imageBase64 = fileStorageService.getMultipartFileInBase64(multipartFile, FilesDirectoriesEnum.ORIGINALS.name());
        createBackgroundDto.setImages(List.of(imageBase64));
        PebblelyResponseDto result = pebblelyService.createBackground(createBackgroundDto);
        fileStorageService.saveFileFromBase64(result.getData(), FilesDirectoriesEnum.CREATED.name(), multipartFile.getOriginalFilename());
    }

    private void inpaintFile(MultipartFile multipartFile, InpaintDto inpaintDto) throws IOException, PebblelyException {
        String imageBase64 = fileStorageService.getMultipartFileInBase64(multipartFile, FilesDirectoriesEnum.ORIGINALS.name());
        inpaintDto.setImage(imageBase64);
        PebblelyResponseDto result = pebblelyService.inpaint(inpaintDto);
        fileStorageService.saveFileFromBase64(result.getData(), FilesDirectoriesEnum.INPAINT.name(), multipartFile.getOriginalFilename());
    }

    /**
     * Builds a BackgroundDto object using the provided parameters.
     *
     * @param <T>         The type of BackgroundDto to build.
     * @param dto         The BackgroundDto object to populate with the parameter values.
     * @param theme       The background theme template from Pebblely.
     * @param description The background description.
     * @param styleColor  The background style color.
     * @param styleImage  The background style image.
     * @param negative    The negative value.
     * @return The populated BackgroundDto object.
     */
    private <T extends BackgroundDto> T buildBackgroundDto(T dto, String theme, String description, String styleColor, MultipartFile styleImage, String negative) throws IOException {
        dto.setDescription(description);
        dto.setNegative(negative);

        if (theme != null && !theme.isEmpty()) {
            dto.setTheme(theme);
        }
        if (styleColor != null && !styleColor.isEmpty()) {
            dto.setStyleColor(styleColor);
        }
        if (styleImage != null && !styleImage.isEmpty()) {
            String imageBase64 = fileStorageService.getMultipartFileInBase64(styleImage, FilesDirectoriesEnum.ORIGINALS.name());
            dto.setStyleImage(imageBase64);
        }

        return dto;
    }
}
