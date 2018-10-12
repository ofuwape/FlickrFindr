package com.example.tfuwape.flickrfindr.core;

import android.content.Context;

import com.example.tfuwape.flickrfindr.modules.ServicesModule;
import com.example.tfuwape.flickrfindr.modules.UtilModule;

import javax.inject.Singleton;

@Singleton
@dagger.Component(modules = {
        ServicesModule.class,
        UtilModule.class
})

/**
 * Provides Initializer class to create new instance of the a Component.
 * @see Component.Initializer
 */
public interface Component extends AppGraph {

    /**
     * Provides init method to create new instance of the a Component.
     */
    final class Initializer {

        private Initializer() {
        }

        /**
         * Creates new instance of component.
         *
         * @param mockMode if API service should be mocked
         * @param context  Context calling method
         * @return new instance of Component
         */
        public static Component init(boolean mockMode, Context context) {
            return DaggerComponent.builder()
                    .servicesModule(new ServicesModule(mockMode, context))
                    .utilModule(new UtilModule(mockMode))
                    .build();
        }
    }
}
