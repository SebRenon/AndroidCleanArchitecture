/*
 *  Copyright (C) 2017 Sebastien Renon Open Source Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.sebrenon.androidcleanarchitecture.presentation.presenter.impl;

import com.sebrenon.androidcleanarchitecture.presentation.presenter.Presenter;
import com.sebrenon.androidcleanarchitecture.presentation.view.View;

import javax.annotation.Nonnull;

/**
 * Created by Seb on 14/09/2017.
 */

public class MainPresenter implements Presenter {

    private View mView;

    @Override
    public void attachView(View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    @Override
    public void searchButtonClicked(@Nonnull String value) {

    }
}
