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

package com.sebrenon.androidcleanarchitecture.quote.data.repository.impl;

import com.sebrenon.androidcleanarchitecture.quote.data.datasource.LocalDataSource;
import com.sebrenon.androidcleanarchitecture.quote.data.datasource.QuoteDataSource;
import com.sebrenon.androidcleanarchitecture.quote.domain.model.QuoteModel;
import com.sebrenon.androidcleanarchitecture.quote.domain.repository.QuoteDataRepository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Seb on 14/09/2017.
 */

public class QuoteDataRepositoryImpl implements QuoteDataRepository {

    @Nonnull
    private final QuoteDataSource mWebDataSource;

    @Nonnull
    private final LocalDataSource mCacheDataSource;

    public QuoteDataRepositoryImpl(@Nonnull QuoteDataSource webDataSource, @Nonnull LocalDataSource cacheDataSource) {
        mWebDataSource = webDataSource;
        mCacheDataSource = cacheDataSource;
    }

    @Nullable
    @Override
    public QuoteModel retrieveQuote(@Nonnull String symbol) {
        QuoteModel quoteModel = mCacheDataSource.fetchQuote(symbol);
        if (quoteModel == null) {
            quoteModel = mWebDataSource.fetchQuote(symbol);
            if (quoteModel != null) {
                // Store in cache
                mCacheDataSource.storeQuote(quoteModel);
            }
        }
        return quoteModel;
    }
}
