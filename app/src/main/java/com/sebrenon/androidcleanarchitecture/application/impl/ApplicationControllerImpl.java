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

package com.sebrenon.androidcleanarchitecture.application.impl;

import com.sebrenon.androidcleanarchitecture.application.ApplicationController;

import javax.annotation.Nonnull;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Seb on 14/09/2017.
 */

public class ApplicationControllerImpl implements ApplicationController {


    private static ApplicationController sInstance;

    private final Retrofit mRetrofit;

    private ApplicationControllerImpl(Retrofit.Builder builder) {
        mRetrofit =
                builder.baseUrl("https://query.yahooapis.com/")
                        .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static void init(Retrofit.Builder builder) {
        sInstance = new ApplicationControllerImpl(builder);
    }

    public static ApplicationController getInstance() {
        return sInstance;
    }

    @Nonnull
    @Override

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
