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

package com.sebrenon.androidcleanarchitecture.quote.network.datasource;

import com.sebrenon.androidcleanarchitecture.quote.domain.model.QuoteModel;
import com.sebrenon.androidcleanarchitecture.quote.network.datasource.impl.WebDataSourceImpl;
import com.sebrenon.androidcleanarchitecture.quote.network.endpoint.QuoteServices;
import com.sebrenon.androidcleanarchitecture.quote.network.model.RemoteQuoteModel;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Seb on 21/09/2017.
 */

public class WebDataSourceImplTest {

    @Test
    public void constructor() {
        QuoteServices quoteServices = mock(QuoteServices.class);

        WebDataSourceImpl webDataSource = new WebDataSourceImpl(quoteServices);

        assertNotNull(webDataSource);
        verifyNoMoreInteractions(quoteServices);
    }

    @Test
    public void fetchQuote_success() throws IOException {

        RemoteQuoteModel remoteQuoteModel = mock(RemoteQuoteModel.class);
        RemoteQuoteModel.QueryBean queryBean = mock(RemoteQuoteModel.QueryBean.class);
        RemoteQuoteModel.QueryBean.ResultsBean resultsBean = mock(RemoteQuoteModel.QueryBean.ResultsBean.class);
        RemoteQuoteModel.QueryBean.ResultsBean.QuoteBean quoteBean = mock(RemoteQuoteModel.QueryBean.ResultsBean.QuoteBean.class);
        QuoteServices quoteServices = mock(QuoteServices.class);
        Call call = mock(Call.class);
        Response response = Response.success(remoteQuoteModel);

        when(quoteServices.fetchQuote(anyString())).thenReturn(call);
        when(call.execute()).thenReturn(response);
        when(remoteQuoteModel.getQuery()).thenReturn(queryBean);
        when(queryBean.getResults()).thenReturn(resultsBean);
        when(resultsBean.getQuote()).thenReturn(quoteBean);
        when(quoteBean.getChange()).thenReturn("change");
        when(quoteBean.getLastTradePriceOnly()).thenReturn("last_price");
        when(quoteBean.getName()).thenReturn("name");
        when(quoteBean.getSymbol()).thenReturn("symbol");

        WebDataSourceImpl webDataSource = new WebDataSourceImpl(quoteServices);

        QuoteModel result = webDataSource.fetchQuote("mySymbol");

        verify(quoteServices).fetchQuote("select * from yahoo.finance.quote where symbol =\"mySymbol\"");
        verifyNoMoreInteractions(quoteServices);

        assertNotNull(result);
        assertEquals(result.getChange(), "change");
        assertEquals(result.getCompany(), "name");
        assertEquals(result.getPrice(), "last_price");
        assertEquals(result.getSymbol(), "symbol");
    }

    @Test
    public void fetchQuote_fail() throws IOException {

        QuoteServices quoteServices = mock(QuoteServices.class);
        Call call = mock(Call.class);
        Response response = Response.success(null);

        when(quoteServices.fetchQuote(anyString())).thenReturn(call);
        when(call.execute()).thenReturn(response);

        WebDataSourceImpl webDataSource = new WebDataSourceImpl(quoteServices);

        QuoteModel result = webDataSource.fetchQuote("mySymbol");

        verify(quoteServices).fetchQuote("select * from yahoo.finance.quote where symbol =\"mySymbol\"");
        verifyNoMoreInteractions(quoteServices);

        assertNull(result);
    }
}
