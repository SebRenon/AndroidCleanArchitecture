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

package com.sebrenon.androidcleanarchitecture.application;

import com.sebrenon.androidcleanarchitecture.application.impl.ApplicationControllerImpl;
import com.sebrenon.androidcleanarchitecture.database.AppDatabase;

import org.junit.Test;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Seb on 21/09/2017.
 */

public class ApplicationControllerImplTest {

    @Test
    public void instantiate() {
        AppDatabase appDatabase = mock(AppDatabase.class);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://test.com/").build();

        ApplicationControllerImpl.init(appDatabase, retrofit);

        assertNotNull(ApplicationControllerImpl.getInstance());
        assertNotNull(ApplicationControllerImpl.getInstance().getRetrofit());
        assertEquals(ApplicationControllerImpl.getInstance().getRetrofit(), retrofit);
        assertNotNull(ApplicationControllerImpl.getInstance().getDatabase());
        assertEquals(ApplicationControllerImpl.getInstance().getDatabase(), appDatabase);
    }
}
