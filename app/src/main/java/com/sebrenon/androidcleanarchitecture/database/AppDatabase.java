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

package com.sebrenon.androidcleanarchitecture.database;

import com.sebrenon.androidcleanarchitecture.cache.dao.QuoteDao;
import com.sebrenon.androidcleanarchitecture.cache.entity.QuoteEntity;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Seb on 15/09/2017.
 */

@Database(entities = {QuoteEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract QuoteDao quoteDao();
}
