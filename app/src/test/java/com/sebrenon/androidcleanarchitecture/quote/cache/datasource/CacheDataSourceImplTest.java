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

package com.sebrenon.androidcleanarchitecture.quote.cache.datasource;

import com.sebrenon.androidcleanarchitecture.application.ApplicationController;
import com.sebrenon.androidcleanarchitecture.database.AppDatabase;
import com.sebrenon.androidcleanarchitecture.quote.cache.dao.QuoteDao;
import com.sebrenon.androidcleanarchitecture.quote.cache.datasource.impl.CacheDataSourceImpl;
import com.sebrenon.androidcleanarchitecture.quote.cache.entity.QuoteEntity;
import com.sebrenon.androidcleanarchitecture.quote.domain.model.QuoteModel;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Seb on 21/09/2017.
 */

public class CacheDataSourceImplTest {

    @Test
    public void constructor() {
        ApplicationController applicationController = mock(ApplicationController.class);
        AppDatabase appDatabase = mock(AppDatabase.class);

        when(applicationController.getDatabase()).thenReturn(appDatabase);

        CacheDataSourceImpl cacheDataSource = new CacheDataSourceImpl(applicationController);

        assertNotNull(cacheDataSource);
        verify(applicationController).getDatabase();
        verifyNoMoreInteractions(applicationController);
    }

    @Test
    public void fetchQuote_success() {
        ApplicationController applicationController = mock(ApplicationController.class);
        AppDatabase appDatabase = mock(AppDatabase.class);
        QuoteDao quoteDao = mock(QuoteDao.class);
        QuoteEntity quoteEntity = new QuoteEntity();
        quoteEntity.setChange("change");
        quoteEntity.setLastPrice("last_price");
        quoteEntity.setSymbol("symbol");
        quoteEntity.setTimestamp(12345L);
        quoteEntity.setUid(4);

        when(applicationController.getDatabase()).thenReturn(appDatabase);
        when(appDatabase.quoteDao()).thenReturn(quoteDao);
        when(quoteDao.findBySymbol(anyString(), anyLong())).thenReturn(quoteEntity);

        CacheDataSourceImpl cacheDataSource = new CacheDataSourceImpl(applicationController);
        QuoteModel result = cacheDataSource.fetchQuote("mySymbol");

        verify(appDatabase).quoteDao();
        verifyNoMoreInteractions(appDatabase);
        verify(quoteDao).findBySymbol(eq("mySymbol"), anyLong());
        verifyNoMoreInteractions(quoteDao);

        assertNotNull(result);
        assertEquals(result.getChange(), "change");
        assertEquals(result.getCompany(), "");
        assertEquals(result.getPrice(), "last_price");
        assertEquals(result.getSymbol(), "symbol*");
    }

    @Test
    public void fetchQuote_fail() {
        ApplicationController applicationController = mock(ApplicationController.class);
        AppDatabase appDatabase = mock(AppDatabase.class);
        QuoteDao quoteDao = mock(QuoteDao.class);

        when(applicationController.getDatabase()).thenReturn(appDatabase);
        when(appDatabase.quoteDao()).thenReturn(quoteDao);
        when(quoteDao.findBySymbol(anyString(), anyLong())).thenReturn(null);

        CacheDataSourceImpl cacheDataSource = new CacheDataSourceImpl(applicationController);
        QuoteModel result = cacheDataSource.fetchQuote("mySymbol");

        verify(appDatabase).quoteDao();
        verifyNoMoreInteractions(appDatabase);
        verify(quoteDao).findBySymbol(eq("mySymbol"), anyLong());
        verifyNoMoreInteractions(quoteDao);

        assertNull(result);
    }


    @Test
    public void storeQuote_success() {
        ApplicationController applicationController = mock(ApplicationController.class);
        AppDatabase appDatabase = mock(AppDatabase.class);
        QuoteDao quoteDao = mock(QuoteDao.class);
        QuoteModel quoteModel = new QuoteModel();
        quoteModel.setChange("change");
        quoteModel.setPrice("last_price");
        quoteModel.setSymbol("symbol");
        quoteModel.setCompany("company");

        when(applicationController.getDatabase()).thenReturn(appDatabase);
        when(appDatabase.quoteDao()).thenReturn(quoteDao);

        ArgumentCaptor<QuoteEntity> argument = ArgumentCaptor.forClass(QuoteEntity.class);

        CacheDataSourceImpl cacheDataSource = new CacheDataSourceImpl(applicationController);
        cacheDataSource.storeQuote(quoteModel);

        verify(appDatabase).quoteDao();
        verifyNoMoreInteractions(appDatabase);
        verify(quoteDao).insertQuote(argument.capture());
        verifyNoMoreInteractions(quoteDao);

        QuoteEntity quoteEntity = argument.getValue();
        assertEquals(quoteEntity.getChange(), "change");
        assertEquals(quoteEntity.getLastPrice(), "last_price");
        assertEquals(quoteEntity.getSymbol(), "symbol");
        assertEquals(quoteEntity.getUid(), 0);
        assertTrue(quoteEntity.getTimestamp() > 0);
    }
}
