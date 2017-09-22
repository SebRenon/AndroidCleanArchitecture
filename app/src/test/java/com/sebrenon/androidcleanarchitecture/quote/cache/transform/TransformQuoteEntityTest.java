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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Seb on 21/09/2017.
 */

public class TransformQuoteEntityTest {

    @Test
    public void transformToDomainModel_success() {
        QuoteEntity quoteEntity = new QuoteEntity();
        quoteEntity.setChange("change");
        quoteEntity.setLastPrice("last_price");
        quoteEntity.setSymbol("symbol");
        quoteEntity.setTimestamp(12345L);
        quoteEntity.setUid(4);
        QuoteModel result = TransformQuoteEntity.transform(quoteEntity);

        assertNotNull(result);
        assertEquals("change", result.getChange());
        assertEquals("last_price", result.getPrice());
        assertEquals("symbol*", result.getSymbol());
        assertEquals("", result.getCompany());
    }

    @Test
    public void transformToDomainModel_fail() {
        QuoteEntity quoteEntity = null;
        QuoteModel result = TransformQuoteEntity.transform(quoteEntity);
        assertNull(result);
    }

    @Test
    public void transformToDataModel_success() {
        QuoteModel quoteModel = new QuoteModel();
        quoteModel.setChange("change");
        quoteModel.setPrice("last_price");
        quoteModel.setSymbol("symbol");
        quoteModel.setCompany("company");
        QuoteEntity result = TransformQuoteEntity.transform(quoteModel);

        assertNotNull(result);
        assertEquals("change", result.getChange());
        assertEquals("last_price", result.getLastPrice());
        assertEquals("symbol", result.getSymbol());
        assertEquals(0, result.getUid());
        assertEquals(0L, result.getTimestamp());
    }
}
