package com.example.tfuwape.flickrfindr.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

class AssetJSONFile {

    static String readByFilename(String filename,
                                 Context mContext) {
        byte[] formArray = new byte[0];
        InputStream file = null;
        final String tagName = "AssetJSONFile";
        final String errTagName = "Error: ";
        try {
            AssetManager manager = mContext.getAssets();
            file = manager.open(filename);
            formArray = new byte[file.available()];
            int count = file.read(formArray);
            Log.d(tagName, "byte counter: " + count);
        } catch (IOException e) {
            Log.e(tagName, String.format("%s%s", errTagName, e));
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException e) {
                Log.e(tagName, String.format("%s%s", errTagName, e));
            }
        }
        return new String(formArray);
    }
}
