package com.example.tfuwape.flickrfindr.modules;

/**
 * Base module class for a mockable module
 */
public abstract class MockableModule {
    protected final boolean mockMode;

    public MockableModule(boolean provideMocks) {
        mockMode = provideMocks;
    }
}
