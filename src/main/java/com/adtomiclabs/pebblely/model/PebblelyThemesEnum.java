package com.adtomiclabs.pebblely.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * Enum representing the Pebblely available themes for background and inpaint creation.
 */
@Getter
@AllArgsConstructor
public enum PebblelyThemesEnum {

    SURPRISE_ME("Surprise me"),

    STUDIO("Studio"),

    OUTDOORS("Outdoors"),

    SILK("Silk"),

    CAFE("Cafe"),

    TABLETOP("Tabletop"),

    KITCHEN("Kitchen"),

    FLOWERS("Flowers"),

    NATURE("Nature"),

    BEACH("Beach"),

    BATHROOM("Bathroom"),

    FURNITURE("Furniture"),

    PAINT("Paint"),

    WATER("Water"),

    PEBBLES("Pebbles"),

    SNOW("Snow");

    private final String name;

    /**
     * Returns a list of all theme names defined in the enum.
     *
     * @return A list of theme names.
     */
    public static List<String> getThemes() {
        return Arrays.stream(PebblelyThemesEnum.values())
                .map(PebblelyThemesEnum::getName)
                .toList();
    }

}
