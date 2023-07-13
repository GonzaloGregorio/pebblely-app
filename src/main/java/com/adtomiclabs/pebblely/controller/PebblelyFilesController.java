package com.adtomiclabs.pebblely.controller;

import com.adtomiclabs.pebblely.model.PebblelyThemesEnum;
import com.adtomiclabs.pebblely.model.request.CreateBackgroundDto;
import com.adtomiclabs.pebblely.model.request.InpaintDto;
import com.adtomiclabs.pebblely.exception.PebblelyException;
import com.adtomiclabs.pebblely.exception.StorageFileNotFoundException;
import com.adtomiclabs.pebblely.service.FileProcessingService;
import com.adtomiclabs.pebblely.service.FileStorageService;
import com.adtomiclabs.pebblely.service.PebblelyService;
import com.adtomiclabs.pebblely.utils.FilesDirectoriesEnum;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

/**
 * Controller class for handling file uploads and transformations in Pebblely application.
 */
@AllArgsConstructor
@Controller
public class PebblelyFilesController {

    public static final String HTML_TEMPLATE = "pebblelyUI";
    public static final String REDIRECT_HOME = "redirect:/";
    public static final String HOST = "http://localhost:8080/files/";

    private final FileStorageService fileStorageService;
    private final FileProcessingService fileProcessingService;
    private final PebblelyService pebblelyService;

    /**
     * Handles the GET request for the root URL ("/") and lists the downloaded files.
     *
     * @param model the model object for rendering the HTML template
     * @return the HTML template to be rendered
     */
    @GetMapping("/")
    public String listDownloadedFiles(Model model) {
        model.addAttribute("host", HOST);
        model.addAttribute("credits", checkCredits());
        model.addAttribute("themes", PebblelyThemesEnum.getThemes());
        for (String subdirectory : FilesDirectoriesEnum.getDirectoriesNames()) {
            model.addAttribute(subdirectory, fileStorageService.loadAll(subdirectory)
                    .map(path -> MvcUriComponentsBuilder.fromMethodName(PebblelyFilesController.class, "serveFile", subdirectory, path.getFileName().toString())
                            .build().toUri().toString())
                    .toList());
        }
        return HTML_TEMPLATE;
    }

    /**
     * Handles the GET request for serving a file from the specified subdirectory and filename.
     *
     * @param subdirectory the subdirectory where the file is located
     * @param filename     the name of the file
     * @return the response entity containing the file as a resource
     */
    @GetMapping("/files/{subdirectory}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String subdirectory, @PathVariable String filename) {
        Resource file = fileStorageService.loadAsResource(subdirectory, filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    /**
     * Handles the GET request for checking the available Pebblely credits.
     *
     * @return the number of available credits
     * @throws PebblelyException if an error occurs while checking the credits
     */
    @GetMapping("/credits")
    public int checkCredits() throws PebblelyException {
        return pebblelyService.getCredits();
    }

    /**
     * Handles the POST request for upscaling images.
     *
     * @param files       the list of uploaded files
     * @param upscaleSize the desired upscale size
     * @return the redirect URL after processing the files
     * @throws IOException       if an I/O error occurs during file processing
     * @throws PebblelyException if an error occurs in the Pebblely service
     */
    @PostMapping("/upscale")
    public String upscale(@RequestParam("files") List<MultipartFile> files, @RequestParam("upscaleSize") int upscaleSize) throws IOException, PebblelyException {
        fileProcessingService.upscaleImages(files, upscaleSize);
        return REDIRECT_HOME;
    }

    /**
     * Handles the POST request for removing the background from images.
     *
     * @param files the list of uploaded files
     * @return the redirect URL after processing the files
     * @throws IOException       if an I/O error occurs during file processing
     * @throws PebblelyException if an error occurs in the Pebblely service
     */
    @PostMapping("/remove-background")
    public String removeBackground(@RequestParam("files") List<MultipartFile> files) throws IOException, PebblelyException {
        fileProcessingService.removeBackgrounds(files);
        return REDIRECT_HOME;
    }

    /**
     * Handles the POST request for creating background images.
     *
     * @param files       the list of uploaded files
     * @param theme       the theme of the background
     * @param description the description of the background
     * @param styleColor  the color style of the background
     * @param styleImage  the image style of the background
     * @param negative    the negative of the background
     * @param height      the height of the background
     * @param width       the width of the background
     * @return the redirect URL after processing the files
     * @throws IOException       if an I/O error occurs during file processing
     * @throws PebblelyException if an error occurs in the Pebblely service
     */
    @PostMapping("/create-background")
    public String createBackground(@RequestParam("files") List<MultipartFile> files,
                                   @RequestParam(value = "theme", required = false) String theme,
                                   @RequestParam(value = "description", required = false) String description,
                                   @RequestParam(value = "styleColor", required = false) String styleColor,
                                   @RequestParam(value = "styleImage", required = false) MultipartFile styleImage,
                                   @RequestParam(value = "negative", required = false) String negative,
                                   @RequestParam(value = "height", required = false) Integer height,
                                   @RequestParam(value = "width", required = false) Integer width)
            throws IOException, PebblelyException {
        CreateBackgroundDto dto = fileProcessingService.buildCreateBackgroundDto(theme, description, styleColor, styleImage, negative, height, width);
        fileProcessingService.createBackgrounds(files, dto);
        return REDIRECT_HOME;
    }


    /**
     * Handles the POST request for inpainting images.
     *
     * @param files       the list of uploaded files
     * @param mask        the mask for inpainting
     * @param theme       the theme of the inpainted image
     * @param description the description of the inpainted image
     * @param styleColor  the color style of the inpainted image
     * @param styleImage  the image style of the inpainted image
     * @param negative    the negative of the inpainted image
     * @return the redirect URL after processing the files
     * @throws IOException       if an I/O error occurs during file processing
     * @throws PebblelyException if an error occurs in the Pebblely service
     */
    @PostMapping("/inpaint")
    public String inpaint(@RequestParam("files") List<MultipartFile> files,
                          @RequestParam("mask") MultipartFile mask,
                          @RequestParam(value = "theme", required = false) String theme,
                          @RequestParam(value = "description", required = false) String description,
                          @RequestParam(value = "styleColor", required = false) String styleColor,
                          @RequestParam(value = "styleImage", required = false) MultipartFile styleImage,
                          @RequestParam(value = "negative", required = false) String negative)
            throws IOException, PebblelyException {

        InpaintDto dto = fileProcessingService.buildInpaintDto(mask, theme, description, styleColor, styleImage, negative);
        fileProcessingService.inpaintFiles(files, dto);
        return REDIRECT_HOME;
    }

    /**
     * Handles the exception for a storage file not found error.
     *
     * @param exc the exception object
     * @return the response entity with a not found status
     */
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    /**
     * Handles the exception for a Pebblely API error.
     *
     * @param exc the exception object
     * @return the response entity with a bad request status and the error message
     */
    @ExceptionHandler(PebblelyException.class)
    public ResponseEntity<?> handlePebblelyApiError(PebblelyException exc) {
        return ResponseEntity.badRequest().body(exc.getMessage());
    }

}

