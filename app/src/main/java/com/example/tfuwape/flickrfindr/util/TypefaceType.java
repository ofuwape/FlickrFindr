package com.example.tfuwape.flickrfindr.util;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public enum TypefaceType {
    ROBOTO_BOLD,
    ROBOTO_LIGHT,
    ROBOTO_REGULAR,
    ROBOTO_MEDIUM,
    ROBOTO_ITALIC;

    public static final Map<TypefaceType, String> fontPathMap;

    static {
        EnumMap<TypefaceType, String> aMap = new EnumMap<>(TypefaceType.class);
        aMap.put(ROBOTO_BOLD, "Roboto-Bold.ttf");
        aMap.put(ROBOTO_LIGHT, "Roboto-Light.ttf");
        aMap.put(ROBOTO_REGULAR, "Roboto-Regular.ttf");
        aMap.put(ROBOTO_MEDIUM, "Roboto-Medium.ttf");
        aMap.put(ROBOTO_ITALIC, "Roboto-Italic.ttf");
        fontPathMap = Collections.unmodifiableMap(aMap);
    }
}

