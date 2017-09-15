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

package com.sebrenon.androidcleanarchitecture.android;

import com.sebrenon.androidcleanarchitecture.application.impl.ApplicationControllerImpl;
import com.sebrenon.androidcleanarchitecture.database.AppDatabase;
import com.sebrenon.androidcleanarchitecture.dependency.AppRegistry;

import android.app.Application;
import android.arch.persistence.room.Room;

import retrofit2.Retrofit;

/**
 * Created by Seb on 14/09/2017.
 */

public class CleanApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppDatabase database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "db-clean-arch").build();
        ApplicationControllerImpl.init(database, new Retrofit.Builder());
        AppRegistry.init();
    }
}
