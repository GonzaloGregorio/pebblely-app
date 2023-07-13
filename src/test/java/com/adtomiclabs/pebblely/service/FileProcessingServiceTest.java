package com.adtomiclabs.pebblely.service;

import com.adtomiclabs.pebblely.model.request.CreateBackgroundDto;
import com.adtomiclabs.pebblely.model.request.InpaintDto;
import com.adtomiclabs.pebblely.model.request.UpscaleDto;
import com.adtomiclabs.pebblely.model.response.PebblelyResponseDto;
import com.adtomiclabs.pebblely.exception.PebblelyException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileProcessingServiceTest {

    @Mock
    private PebblelyService pebblelyService;

    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private FileProcessingService fileProcessingService;

    @Test
    void upscaleImages() throws IOException, PebblelyException {
        List<MultipartFile> multipartFiles = Collections.singletonList(mock(MultipartFile.class));
        int size = 100;

        when(fileStorageService.getMultipartFileInBase64(any(), any())).thenReturn("base64Image");
        when(pebblelyService.upscale(any(UpscaleDto.class))).thenReturn(mock(PebblelyResponseDto.class));

        assertDoesNotThrow(() -> fileProcessingService.upscaleImages(multipartFiles, size));

        verify(fileStorageService, times(1)).getMultipartFileInBase64(any(), any());
        verify(pebblelyService, times(1)).upscale(any(UpscaleDto.class));
        verify(fileStorageService, times(1)).saveFileFromBase64(any(), any(), any());
    }

    @Test
    void upscaleImages_shouldThrowIOException() throws IOException, PebblelyException {
        List<MultipartFile> multipartFiles = Collections.singletonList(mock(MultipartFile.class));
        int size = 100;

        when(fileStorageService.getMultipartFileInBase64(any(), any())).thenThrow(IOException.class);

        assertThrows(IOException.class, () -> fileProcessingService.upscaleImages(multipartFiles, size));

        verify(fileStorageService, times(1)).getMultipartFileInBase64(any(), any());
        verify(pebblelyService, never()).upscale(any(UpscaleDto.class));
        verify(fileStorageService, never()).saveFileFromBase64(any(), any(), any());
    }

    @Test
    void removeBackgrounds() throws IOException, PebblelyException {
        List<MultipartFile> multipartFiles = Collections.singletonList(mock(MultipartFile.class));

        when(fileStorageService.getMultipartFileInBase64(any(), any())).thenReturn("base64Image");
        when(pebblelyService.removeBackground(any())).thenReturn(mock(PebblelyResponseDto.class));

        assertDoesNotThrow(() -> fileProcessingService.removeBackgrounds(multipartFiles));

        verify(fileStorageService, times(1)).getMultipartFileInBase64(any(), any());
        verify(pebblelyService, times(1)).removeBackground(any());
        verify(fileStorageService, times(1)).saveFileFromBase64(any(), any(), any());
    }

    @Test
    void removeBackgrounds_shouldThrowIOException() throws IOException, PebblelyException {
        List<MultipartFile> multipartFiles = Collections.singletonList(mock(MultipartFile.class));

        when(fileStorageService.getMultipartFileInBase64(any(), any())).thenThrow(IOException.class);

        assertThrows(IOException.class, () -> fileProcessingService.removeBackgrounds(multipartFiles));

        verify(fileStorageService, times(1)).getMultipartFileInBase64(any(), any());
        verify(pebblelyService, never()).removeBackground(any());
        verify(fileStorageService, never()).saveFileFromBase64(any(), any(), any());
    }

    @Test
    void createBackgrounds() throws IOException, PebblelyException {
        List<MultipartFile> multipartFiles = Collections.singletonList(mock(MultipartFile.class));
        CreateBackgroundDto createBackgroundDto = mockCreateBackgroundDto();

        when(fileStorageService.getMultipartFileInBase64(any(), any())).thenReturn("base64Image");
        when(pebblelyService.createBackground(any())).thenReturn(mock(PebblelyResponseDto.class));

        assertDoesNotThrow(() -> fileProcessingService.createBackgrounds(multipartFiles, createBackgroundDto));

        verify(fileStorageService, times(1)).getMultipartFileInBase64(any(), any());
        verify(pebblelyService, times(1)).createBackground(any());
        verify(fileStorageService, times(1)).saveFileFromBase64(any(), any(), any());
    }

    @Test
    void createBackgrounds_shouldThrowIOException() throws IOException, PebblelyException {
        List<MultipartFile> multipartFiles = Collections.singletonList(mock(MultipartFile.class));
        CreateBackgroundDto createBackgroundDto = mockCreateBackgroundDto();

        when(fileStorageService.getMultipartFileInBase64(any(), any())).thenThrow(IOException.class);

        assertThrows(IOException.class, () -> fileProcessingService.createBackgrounds(multipartFiles, createBackgroundDto));

        verify(fileStorageService, times(1)).getMultipartFileInBase64(any(), any());
        verify(pebblelyService, never()).createBackground(any());
        verify(fileStorageService, never()).saveFileFromBase64(any(), any(), any());
    }

    @Test
    void inpaintFiles() throws IOException, PebblelyException {
        List<MultipartFile> multipartFiles = Collections.singletonList(mock(MultipartFile.class));
        InpaintDto inpaintDto = mockInpaintDto();

        when(fileStorageService.getMultipartFileInBase64(any(), any())).thenReturn("base64Image");
        when(pebblelyService.inpaint(any())).thenReturn(mock(PebblelyResponseDto.class));

        assertDoesNotThrow(() -> fileProcessingService.inpaintFiles(multipartFiles, inpaintDto));

        verify(fileStorageService, times(1)).getMultipartFileInBase64(any(), any());
        verify(pebblelyService, times(1)).inpaint(any());
        verify(fileStorageService, times(1)).saveFileFromBase64(any(), any(), any());
    }

    @Test
    void inpaintFiles_shouldThrowIOException() throws IOException, PebblelyException {
        List<MultipartFile> multipartFiles = Collections.singletonList(mock(MultipartFile.class));
        InpaintDto inpaintDto = mockInpaintDto();

        when(fileStorageService.getMultipartFileInBase64(any(), any())).thenThrow(IOException.class);

        assertThrows(IOException.class, () -> fileProcessingService.inpaintFiles(multipartFiles, inpaintDto));

        verify(fileStorageService, times(1)).getMultipartFileInBase64(any(), any());
        verify(pebblelyService, never()).inpaint(any());
        verify(fileStorageService, never()).saveFileFromBase64(any(), any(), any());
    }

    private CreateBackgroundDto mockCreateBackgroundDto() {
        String theme = "theme";
        String description = "description";
        String styleColor = "color";
        String styleImage = "image";
        String negative = "negative";
        Integer height = 100;
        Integer width = 200;

        return CreateBackgroundDto.builder()
                .theme(theme)
                .description(description)
                .styleColor(styleColor)
                .styleImage(styleImage)
                .negative(negative)
                .height(height)
                .width(width)
                .build();    }

    private InpaintDto mockInpaintDto() {
        String mask = "mask";
        String theme = "theme";
        String description = "description";
        String styleColor = "color";
        String styleImage = "image";
        String negative = "negative";

        return InpaintDto.builder()
                .mask(mask)
                .theme(theme)
                .description(description)
                .styleColor(styleColor)
                .styleImage(styleImage)
                .negative(negative)
                .build();
    }

}
