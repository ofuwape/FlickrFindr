package com.example.tfuwape.flickrfindr.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class TypefaceManagerTest {

    private TypefaceManager mTypefaceManager;
    private AssetManager mAssetManager;

    @Before
    public void setUp() {
        Context mContext = getInstrumentation().getTargetContext();
        mTypefaceManager = new TypefaceManager();
        mAssetManager = mContext.getAssets();
    }

    @Test
    public void testManagerReturnsTypeface() {
        Typeface typeface = mTypefaceManager.compiledTypefaceWithType(mAssetManager,
                TypefaceType.ROBOTO_REGULAR);
        assertThat(typeface).isNotNull();
    }

    @Test
    public void testManagerReturnsCachedTypeface() {
        Typeface typeface1 = mTypefaceManager.compiledTypefaceWithType(mAssetManager,
                TypefaceType.ROBOTO_BOLD);
        assertThat(typeface1).isNotNull();
        Typeface typeface2 = mTypefaceManager.compiledTypefaceWithType(mAssetManager,
                TypefaceType.ROBOTO_BOLD);
        assertThat(typeface2).isNotNull();
        assertThat(typeface2).isEqualTo(typeface1);
    }

    @Test
    public void testOpenRobotoBoldTypeface() {
        Typeface typeface = mTypefaceManager.compiledTypefaceWithType(mAssetManager,
                TypefaceType.ROBOTO_BOLD);
        assertThat(typeface).isNotNull();
    }

    @Test
    public void testOpenRobotoLightTypeface() {
        Typeface typeface = mTypefaceManager.compiledTypefaceWithType(mAssetManager,
                TypefaceType.ROBOTO_LIGHT);
        assertThat(typeface).isNotNull();
    }

    @Test
    public void testOpenRobotoRegularTypeface() {
        Typeface typeface = mTypefaceManager.compiledTypefaceWithType(mAssetManager,
                TypefaceType.ROBOTO_REGULAR);
        assertThat(typeface).isNotNull();
    }

    @Test
    public void testOpenRobotoMediumTypeface() {
        Typeface typeface = mTypefaceManager.compiledTypefaceWithType(mAssetManager,
                TypefaceType.ROBOTO_MEDIUM);
        assertThat(typeface).isNotNull();
    }
}
