package com.adtomiclabs.pebblely.service;

import com.adtomiclabs.pebblely.config.StoragePropertiesConfig;
import com.adtomiclabs.pebblely.exception.StorageException;
import com.adtomiclabs.pebblely.exception.StorageFileNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Service class for file storage operations.
 */
@AllArgsConstructor
@Service
public class FileStorageService {

    private final StoragePropertiesConfig storagePropertiesConfig;

    /**
     * Converts a MultipartFile to a File and stores it in the specified subdirectory.
     *
     * @param multipartFile The MultipartFile to convert.
     * @param subdirectory  The subdirectory to store the file in.
     * @return The Base64-encoded representation of the stored file.
     * @throws IOException If an I/O error occurs during the conversion or storage.
     */
    public String getMultipartFileInBase64(MultipartFile multipartFile, String subdirectory) throws IOException {
        File file = convertMultipartFileToFile(multipartFile, subdirectory);
        return encodeFileToBase64(file);
    }

    /**
     * Saves a file from a Base64-encoded string representation.
     *
     * @param imageBase64  The Base64-encoded string representing the file.
     * @param subdirectory The subdirectory to store the file in.
     * @param fileName     The name of the file.
     * @throws IOException If an I/O error occurs during the storage.
     */
    public void saveFileFromBase64(String imageBase64, String subdirectory, String fileName) throws IOException {
        byte[] decodedBytes = Base64.decodeBase64(imageBase64);
        File directory = new File(storagePropertiesConfig.getLocation(), subdirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, fileName);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(decodedBytes);
        }
    }

    /**
     * Loads all files in the specified subdirectory.
     *
     * @param subdirectory The subdirectory to load files from.
     * @return A Stream of Path objects representing the loaded files.
     * @throws StorageException If an error occurs during the file loading process.
     */
    public Stream<Path> loadAll(String subdirectory) {
        try {
            Path subdirectoryPath = Path.of(storagePropertiesConfig.getLocation(), subdirectory);
            return Files.walk(subdirectoryPath, 2)
                    .filter(path -> !path.equals(subdirectoryPath))
                    .map(subdirectoryPath::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    /**
     * Loads a file from the specified subdirectory as a Resource.
     *
     * @param subdirectory The subdirectory of the file.
     * @param filename     The name of the file.
     * @return The loaded file as a Resource object.
     * @throws StorageFileNotFoundException If the file cannot be found or accessed.
     */
    public Resource loadAsResource(String subdirectory, String filename) {
        try {
            Path file = load(subdirectory, filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    private Path load(String subdirectory, String filename) {
        Path subdirectoryPath = Path.of(storagePropertiesConfig.getLocation(), subdirectory);
        return subdirectoryPath.resolve(filename);
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile, String subdirectory) throws IOException {
        File directory = new File(storagePropertiesConfig.getLocation(), subdirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(multipartFile.getBytes());
        }
        return file;
    }

    private String encodeFileToBase64(File file) throws IOException {
        byte[] bytes;
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            bytes = new byte[(int) file.length()];
            fileInputStream.read(bytes);
        }
        return new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
    }

}

