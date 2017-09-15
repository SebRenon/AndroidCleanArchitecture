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

package com.sebrenon.androidcleanarchitecture.quote.cache.dao;

import com.sebrenon.androidcleanarchitecture.quote.cache.entity.QuoteEntity;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

/**
 * Created by Seb on 14/09/2017.
 */

@Dao
public interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQuote(QuoteEntity... quoteEntities);

    @Query("SELECT * FROM quote WHERE symbol LIKE :query_symbol AND timestamp >= :period LIMIT 1")
    QuoteEntity findBySymbol(String query_symbol, long period);
}
