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

package com.sebrenon.androidcleanarchitecture.network.datasource.transform;

import com.sebrenon.androidcleanarchitecture.domain.model.QuoteModel;
import com.sebrenon.androidcleanarchitecture.network.model.RemoteQuoteModel;

import javax.annotation.Nullable;

/**
 * Created by Seb on 14/09/2017.
 */

public class TransformQuoteData {

    @Nullable
    public static QuoteModel transform(RemoteQuoteModel remoteQuoteModel) {
        QuoteModel quoteModel = new QuoteModel();
        if (remoteQuoteModel != null && remoteQuoteModel.getQuery() != null && remoteQuoteModel.getQuery().getResults() != null && remoteQuoteModel.getQuery().getResults().getQuote() != null && remoteQuoteModel.getQuery().getResults().getQuote().getName() != null) {
            RemoteQuoteModel.QueryBean.ResultsBean.QuoteBean quote = remoteQuoteModel.getQuery().getResults().getQuote();
            quoteModel.setCompany(quote.getName());
            quoteModel.setPrice(quote.getLastTradePriceOnly());
            quoteModel.setSymbol(quote.getSymbol());
            quoteModel.setChange(quote.getChange());
            return quoteModel;
        } else {
            return null;
        }
    }
}
