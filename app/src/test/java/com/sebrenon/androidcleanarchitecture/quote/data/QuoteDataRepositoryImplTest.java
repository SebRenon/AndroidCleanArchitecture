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

package com.sebrenon.androidcleanarchitecture.quote.data;

import com.sebrenon.androidcleanarchitecture.quote.data.datasource.LocalDataSource;
import com.sebrenon.androidcleanarchitecture.quote.data.datasource.QuoteDataSource;
import com.sebrenon.androidcleanarchitecture.quote.data.repository.impl.QuoteDataRepositoryImpl;
import com.sebrenon.androidcleanarchitecture.quote.domain.model.QuoteModel;
import com.sebrenon.androidcleanarchitecture.quote.domain.repository.QuoteDataRepository;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Seb on 21/09/2017.
 */

public class QuoteDataRepositoryImplTest {

    @Test
    public void constructor() {
        QuoteDataSource quoteDataSource = mock(QuoteDataSource.class);
        LocalDataSource localDataSource = mock(LocalDataSource.class);

        QuoteDataRepository quoteDataRepository = new QuoteDataRepositoryImpl(quoteDataSource, localDataSource);

        assertNotNull(quoteDataRepository);
    }

    @Test
    public void retrieveQuote_fromCache() {
        QuoteDataSource quoteDataSource = mock(QuoteDataSource.class);
        LocalDataSource localDataSource = mock(LocalDataSource.class);
        QuoteModel quoteModel = new QuoteModel();
        quoteModel.setChange("change");
        quoteModel.setPrice("last_price");
        quoteModel.setSymbol("symbol");
        quoteModel.setCompany("company");

        QuoteDataRepository quoteDataRepository = new QuoteDataRepositoryImpl(quoteDataSource, localDataSource);

        when(localDataSource.fetchQuote(eq("mySymbol"))).thenReturn(quoteModel);

        QuoteModel result = quoteDataRepository.retrieveQuote("mySymbol");

        verify(localDataSource).fetchQuote("mySymbol");
        verifyNoMoreInteractions(localDataSource);
        verifyNoMoreInteractions(quoteDataSource);

        assertNotNull(result);
        assertEquals(result.getSymbol(), "symbol");
        assertEquals(result.getChange(), "change");
        assertEquals(result.getPrice(), "last_price");
        assertEquals(result.getCompany(), "company");
    }

    @Test
    public void retrieveQuote_fromRemoteSource() {
        QuoteDataSource quoteDataSource = mock(QuoteDataSource.class);
        LocalDataSource localDataSource = mock(LocalDataSource.class);
        QuoteModel quoteModel = new QuoteModel();
        quoteModel.setChange("change");
        quoteModel.setPrice("last_price");
        quoteModel.setSymbol("symbol");
        quoteModel.setCompany("company");

        QuoteDataRepository quoteDataRepository = new QuoteDataRepositoryImpl(quoteDataSource, localDataSource);

        when(localDataSource.fetchQuote(eq("mySymbol"))).thenReturn(null);
        when(quoteDataSource.fetchQuote("mySymbol")).thenReturn(quoteModel);

        QuoteModel result = quoteDataRepository.retrieveQuote("mySymbol");

        verify(localDataSource).fetchQuote("mySymbol");
        verify(localDataSource).storeQuote(quoteModel);
        verifyNoMoreInteractions(localDataSource);
        verify(quoteDataSource).fetchQuote("mySymbol");
        verifyNoMoreInteractions(quoteDataSource);

        assertNotNull(result);
        assertEquals(result.getSymbol(), "symbol");
        assertEquals(result.getChange(), "change");
        assertEquals(result.getPrice(), "last_price");
        assertEquals(result.getCompany(), "company");
    }

    @Test
    public void retrieveQuote_fail() {
        QuoteDataSource quoteDataSource = mock(QuoteDataSource.class);
        LocalDataSource localDataSource = mock(LocalDataSource.class);

        QuoteDataRepository quoteDataRepository = new QuoteDataRepositoryImpl(quoteDataSource, localDataSource);

        when(localDataSource.fetchQuote(eq("mySymbol"))).thenReturn(null);
        when(quoteDataSource.fetchQuote("mySymbol")).thenReturn(null);

        QuoteModel result = quoteDataRepository.retrieveQuote("mySymbol");

        verify(localDataSource).fetchQuote("mySymbol");
        verifyNoMoreInteractions(localDataSource);
        verify(quoteDataSource).fetchQuote("mySymbol");
        verifyNoMoreInteractions(quoteDataSource);

        assertNull(result);
    }
}
