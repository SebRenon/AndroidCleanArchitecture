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

package com.sebrenon.androidcleanarchitecture.cache.datasource.impl;

import com.sebrenon.androidcleanarchitecture.application.ApplicationController;
import com.sebrenon.androidcleanarchitecture.cache.entity.QuoteEntity;
import com.sebrenon.androidcleanarchitecture.cache.transform.TransformQuoteEntity;
import com.sebrenon.androidcleanarchitecture.data.datasource.LocalDataSource;
import com.sebrenon.androidcleanarchitecture.database.AppDatabase;
import com.sebrenon.androidcleanarchitecture.domain.model.QuoteModel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Seb on 14/09/2017.
 */

public class CacheDataSourceImpl implements LocalDataSource {

    @Nonnull
    private final AppDatabase mAppDatabase;

    public CacheDataSourceImpl(@Nonnull ApplicationController applicationController) {
        mAppDatabase = applicationController.getDatabase();
    }

    @Nullable
    @Override
    public QuoteModel fetchQuote(@Nonnull String symbol) {
        QuoteEntity quoteEntity = mAppDatabase.quoteDao().findBySymbol(symbol, System.currentTimeMillis() - 1000 * 5 * 60);
        return TransformQuoteEntity.transform(quoteEntity);
    }

    @Override
    public void storeQuote(@Nonnull QuoteModel quoteModel) {
        QuoteEntity quoteEntity = TransformQuoteEntity.transform(quoteModel);
        quoteEntity.setTimestamp(System.currentTimeMillis());
        mAppDatabase.quoteDao().insertQuote(quoteEntity);
    }
}
