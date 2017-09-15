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

package com.sebrenon.androidcleanarchitecture.quote.cache.transform;

import com.sebrenon.androidcleanarchitecture.quote.cache.entity.QuoteEntity;
import com.sebrenon.androidcleanarchitecture.quote.domain.model.QuoteModel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Seb on 15/09/2017.
 */

public class TransformQuoteEntity {

    @Nullable
    public static QuoteModel transform(@Nullable QuoteEntity quoteEntity) {
        if (quoteEntity != null) {
            QuoteModel quoteModel = new QuoteModel();
            // to inform it's cached, added *
            quoteModel.setSymbol(quoteEntity.getSymbol() + "*");
            quoteModel.setPrice(quoteEntity.getLastPrice());
            quoteModel.setCompany("");
            quoteModel.setChange(quoteEntity.getChange());
            return quoteModel;
        } else {
            return null;
        }
    }

    public static QuoteEntity transform(@Nonnull QuoteModel quoteModel) {

        QuoteEntity quoteEntity = new QuoteEntity();
        quoteEntity.setSymbol(quoteModel.getSymbol());
        quoteEntity.setLastPrice(quoteModel.getPrice());
        quoteEntity.setChange(quoteModel.getChange());
        return quoteEntity;

    }
}
