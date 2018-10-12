package com.example.tfuwape.flickrfindr.util;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import java.util.EnumMap;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TypefaceManager {

    private static EnumMap<TypefaceType, Typeface> mTypefaceCache = new EnumMap<>(TypefaceType.class);

    @Inject
    public TypefaceManager() {
        //default constructor
    }

    public Typeface compiledTypefaceWithType(AssetManager assetManager, TypefaceType type) {
        Typeface typeface = mTypefaceCache.get(type);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(assetManager, TypefaceType.fontPathMap.get(type));
            if (typeface != null) {
                mTypefaceCache.put(type, typeface);
            }
        }
        return typeface;
    }
}
