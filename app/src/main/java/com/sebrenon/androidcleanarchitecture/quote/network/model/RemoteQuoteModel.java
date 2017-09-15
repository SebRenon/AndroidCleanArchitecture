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

package com.sebrenon.androidcleanarchitecture.quote.network.model;

import javax.annotation.Nullable;

/**
 * Created by Seb on 14/09/2017.
 */

public class RemoteQuoteModel {

    @Nullable
    private QueryBean query;

    @Nullable
    public QueryBean getQuery() {
        return query;
    }

    public static class QueryBean {

        @Nullable
        private ResultsBean results;

        @Nullable
        public ResultsBean getResults() {
            return results;
        }

        public static class ResultsBean {

            @Nullable
            private QuoteBean quote;

            @Nullable
            public QuoteBean getQuote() {
                return quote;
            }

            public static class QuoteBean {

                @Nullable
                private String Change;

                @Nullable
                private String LastTradePriceOnly;

                @Nullable
                private String Name;

                @Nullable
                private String Symbol;

                @Nullable
                public String getChange() {
                    return Change;
                }

                @Nullable
                public String getLastTradePriceOnly() {
                    return LastTradePriceOnly;
                }

                @Nullable
                public String getName() {
                    return Name;
                }

                @Nullable
                public String getSymbol() {
                    return Symbol;
                }
            }
        }
    }
}
