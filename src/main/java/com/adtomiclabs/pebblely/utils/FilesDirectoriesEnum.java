package com.adtomiclabs.pebblely.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * Enum representing the directories for storing different types of files.
 */
@Getter
@AllArgsConstructor
public enum FilesDirectoriesEnum {

    ORIGINALS("originals"),
    UPSCALE("upscale"),
    REMOVED("removed"),
    CREATED("created"),
    INPAINT("inpaint");

    private final String name;

    /**
     * Returns a list of all directory names defined in the enum.
     *
     * @return A list of directory names.
     */
    public static List<String> getDirectoriesNames() {
        return Arrays.stream(FilesDirectoriesEnum.values())
                .map(FilesDirectoriesEnum::getName)
                .toList();
    }
}
