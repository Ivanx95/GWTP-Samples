package com.gwtplatform.samples.basic.client.gin;

import javax.inject.Inject;

import com.gwtplatform.mvp.client.Bootstrapper;
import com.gwtplatform.mvp.client.annotations.IsTheBootstrapper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

@IsTheBootstrapper
public class BootstrapperImpl implements Bootstrapper {
    private final PlaceManager placeManager;

    @Inject
    public BootstrapperImpl(PlaceManager placeManager) {
        this.placeManager = placeManager;
    }

    @Override
    public void init() {
        placeManager.revealCurrentPlace();
    }
}