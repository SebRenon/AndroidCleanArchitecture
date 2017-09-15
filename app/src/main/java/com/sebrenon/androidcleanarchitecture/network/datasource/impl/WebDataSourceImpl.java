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

package com.sebrenon.androidcleanarchitecture.network.datasource.impl;

import com.sebrenon.androidcleanarchitecture.application.ApplicationController;
import com.sebrenon.androidcleanarchitecture.data.datasource.QuoteDataSource;
import com.sebrenon.androidcleanarchitecture.domain.model.QuoteModel;
import com.sebrenon.androidcleanarchitecture.network.transform.TransformQuoteData;
import com.sebrenon.androidcleanarchitecture.network.endpoint.QuoteEndpoint;
import com.sebrenon.androidcleanarchitecture.network.model.RemoteQuoteModel;

import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Seb on 14/09/2017.
 */

public class WebDataSourceImpl implements QuoteDataSource {

    private final Retrofit mRetrofit;

    public WebDataSourceImpl(ApplicationController applicationController) {
        this.mRetrofit = applicationController.getRetrofit();
    }

    @Nullable
    @Override
    public QuoteModel fetchQuote(@Nonnull String symbol) {
        QuoteEndpoint service = mRetrofit.create(QuoteEndpoint.class);
        String query = "select * from yahoo.finance.quote where symbol =\"" + symbol + "\"";
        Response<RemoteQuoteModel> remoteQuoteModel;
        try {
            remoteQuoteModel = service.fetchQuote(query).execute();
        } catch (IOException e) {
            return null;
        }

        return TransformQuoteData.transform(remoteQuoteModel.body());
    }
}
