package com.example.tfuwape.flickrfindr.modules;

import com.example.tfuwape.flickrfindr.util.TypefaceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provides mock objects for Util components
 */
@Module
public final class UtilModule extends MockableModule {

    /**
     * Creates new instance of UtilModule.
     *
     * @param provideMocks boolean check mock mode
     */
    public UtilModule(boolean provideMocks) {
        super(provideMocks);
    }

    /**
     * Provides instance of TypefaceManager.
     *
     * @return TypefaceManager
     */
    @Provides
    @Singleton
    TypefaceManager provideManager() {
        return new TypefaceManager();
    }

}
