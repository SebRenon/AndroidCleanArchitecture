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

package com.sebrenon.androidcleanarchitecture.quote.domain.interactor.impl;

import com.sebrenon.androidcleanarchitecture.quote.domain.exception.InvalidSymbolFormat;
import com.sebrenon.androidcleanarchitecture.quote.domain.exception.QuoteNotFoundException;
import com.sebrenon.androidcleanarchitecture.quote.domain.interactor.RequestQuoteUseCase;
import com.sebrenon.androidcleanarchitecture.quote.domain.model.QuoteModel;
import com.sebrenon.androidcleanarchitecture.quote.domain.repository.QuoteDataRepository;

import java.util.concurrent.Callable;

import javax.annotation.Nonnull;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;

/**
 * Created by Seb on 14/09/2017.
 */

public class RequestQuoteUseCaseImpl implements RequestQuoteUseCase {

    @Nonnull
    private Scheduler mSubscriberScheduler;

    @Nonnull
    private Scheduler mObserverScheduler;

    @Nonnull
    private QuoteDataRepository mQuoteDataRepository;

    public RequestQuoteUseCaseImpl(@Nonnull QuoteDataRepository quoteDataRepository, @Nonnull Scheduler subscriberScheduler, @Nonnull Scheduler observerScheduler) {
        this.mQuoteDataRepository = quoteDataRepository;
        this.mSubscriberScheduler = subscriberScheduler;
        this.mObserverScheduler = observerScheduler;
    }

    @Override
    public Observable<String> getQuote(@Nonnull final String userInput) {
        return Observable.fromCallable(new Callable<String>() {

            @Override
            public String call() throws Exception {
                // validate input
                if (userInput.isEmpty() || userInput.length() > 5) {
                    throw new InvalidSymbolFormat();
                }
                return userInput;
            }
        }).map(new Function<String, QuoteModel>() {
            @Override
            public QuoteModel apply(String s) throws Exception {
                // request data from repository
                QuoteModel response = RequestQuoteUseCaseImpl.this.mQuoteDataRepository.retrieveQuote(s);
                if (response == null) {
                    throw new QuoteNotFoundException();
                } else {
                    return response;
                }
            }
        }).map(new Function<QuoteModel, String>() {
            @Override
            public String apply(QuoteModel quoteModel) throws Exception {
                return quoteModel.getSymbol() + " @ $" + quoteModel.getPrice() + " (" + quoteModel.getChange() + "%)";
            }
        }).observeOn(mObserverScheduler).subscribeOn(mSubscriberScheduler);
    }
}
