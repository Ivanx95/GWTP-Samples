/**
 * Copyright 2011 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gwtplatform.samples.basicspring.client.application;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.gwtplatform.samples.basicspring.client.place.NameTokens;
import com.gwtplatform.samples.basicspring.client.place.TokenParameters;
import com.gwtplatform.samples.basicspring.shared.FieldVerifier;

public class ApplicationPresenter extends Presenter<ApplicationPresenter.MyView, ApplicationPresenter.MyProxy>
        implements ApplicationUiHandlers {
    /**
     * {@link ApplicationPresenter}'s proxy.
     */
    @ProxyStandard
    @NameToken(NameTokens.home)
    public interface MyProxy extends ProxyPlace<ApplicationPresenter> {
    }

    /**
     * {@link ApplicationPresenter}'s view.
     */
    public interface MyView extends View, HasUiHandlers<ApplicationUiHandlers> {
        void resetAndFocus();

        void setError(String errorText);
    }

    private final PlaceManager placeManager;

    @Inject
    ApplicationPresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy,
            PlaceManager placeManager) {
        super(eventBus, view, proxy, RevealType.Root);

        this.placeManager = placeManager;

        getView().setUiHandlers(this);
    }

    @Override
    public void sendName(String name) {
        sendNameToServer(name);
    }

    @Override
    protected void onReset() {
        super.onReset();

        getView().resetAndFocus();
    }

    /**
     * Send the name from the nameField to the server and wait for a response.
     */
    private void sendNameToServer(String name) {
        getView().setError("");
        if (!FieldVerifier.isValidName(name)) {
            getView().setError("<p><em>Please enter at least four characters</em></p>");
            return;
        }

        PlaceRequest responsePlaceRequest = new PlaceRequest.Builder()
                .nameToken(NameTokens.response)
                .with(TokenParameters.TEXT_TO_SERVER, name)
                .build();
        placeManager.revealPlace(responsePlaceRequest);
    }
}
