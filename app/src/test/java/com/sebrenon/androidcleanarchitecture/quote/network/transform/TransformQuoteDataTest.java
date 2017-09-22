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

package com.sebrenon.androidcleanarchitecture.quote.network.transform;

import com.sebrenon.androidcleanarchitecture.quote.domain.model.QuoteModel;
import com.sebrenon.androidcleanarchitecture.quote.network.model.RemoteQuoteModel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Seb on 21/09/2017.
 */

public class TransformQuoteDataTest {

    @Test
    public void transformToDomainModel_success() {
        RemoteQuoteModel remoteQuoteModel = mock(RemoteQuoteModel.class);
        RemoteQuoteModel.QueryBean queryBean = mock(RemoteQuoteModel.QueryBean.class);
        RemoteQuoteModel.QueryBean.ResultsBean resultsBean = mock(RemoteQuoteModel.QueryBean.ResultsBean.class);
        RemoteQuoteModel.QueryBean.ResultsBean.QuoteBean quoteBean = mock(RemoteQuoteModel.QueryBean.ResultsBean.QuoteBean.class);

        when(remoteQuoteModel.getQuery()).thenReturn(queryBean);
        when(queryBean.getResults()).thenReturn(resultsBean);
        when(resultsBean.getQuote()).thenReturn(quoteBean);
        when(quoteBean.getChange()).thenReturn("change");
        when(quoteBean.getLastTradePriceOnly()).thenReturn("last_price");
        when(quoteBean.getName()).thenReturn("name");
        when(quoteBean.getSymbol()).thenReturn("symbol");

        QuoteModel result = TransformQuoteData.transform(remoteQuoteModel);

        assertNotNull(result);
        assertEquals("change", result.getChange());
        assertEquals("last_price", result.getPrice());
        assertEquals("symbol", result.getSymbol());
        assertEquals("name", result.getCompany());
    }

    @Test
    public void transformToDomainModel_fail() {
        QuoteModel result = TransformQuoteData.transform(null);
        assertNull(result);

        RemoteQuoteModel remoteQuoteModel = mock(RemoteQuoteModel.class);
        RemoteQuoteModel.QueryBean queryBean = mock(RemoteQuoteModel.QueryBean.class);
        RemoteQuoteModel.QueryBean.ResultsBean resultsBean = mock(RemoteQuoteModel.QueryBean.ResultsBean.class);
        RemoteQuoteModel.QueryBean.ResultsBean.QuoteBean quoteBean = mock(RemoteQuoteModel.QueryBean.ResultsBean.QuoteBean.class);

        when(remoteQuoteModel.getQuery()).thenReturn(queryBean);
        result = TransformQuoteData.transform(null);
        assertNull(result);

        when(queryBean.getResults()).thenReturn(resultsBean);
        result = TransformQuoteData.transform(null);
        assertNull(result);

        when(resultsBean.getQuote()).thenReturn(quoteBean);
        result = TransformQuoteData.transform(null);
        assertNull(result);
    }
}
