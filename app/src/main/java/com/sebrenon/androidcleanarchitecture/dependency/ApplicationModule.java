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

package com.sebrenon.androidcleanarchitecture.dependency;

import com.sebrenon.androidcleanarchitecture.application.ApplicationController;
import com.sebrenon.androidcleanarchitecture.application.impl.ApplicationControllerImpl;
import com.sebrenon.androidcleanarchitecture.quote.cache.datasource.impl.CacheDataSourceImpl;
import com.sebrenon.androidcleanarchitecture.quote.data.datasource.LocalDataSource;
import com.sebrenon.androidcleanarchitecture.quote.data.datasource.QuoteDataSource;
import com.sebrenon.androidcleanarchitecture.quote.data.repository.impl.QuoteDataRepositoryImpl;
import com.sebrenon.androidcleanarchitecture.quote.domain.interactor.RequestQuoteUseCase;
import com.sebrenon.androidcleanarchitecture.quote.domain.interactor.impl.RequestQuoteUseCaseImpl;
import com.sebrenon.androidcleanarchitecture.quote.domain.repository.QuoteDataRepository;
import com.sebrenon.androidcleanarchitecture.quote.network.datasource.impl.WebDataSourceImpl;
import com.sebrenon.androidcleanarchitecture.quote.presentation.presenter.Presenter;
import com.sebrenon.androidcleanarchitecture.quote.presentation.presenter.impl.MainPresenter;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Seb on 15/09/2017.
 */

@Module
public class ApplicationModule {

    @Provides
    ApplicationController applicationController() {
        return ApplicationControllerImpl.getInstance();
    }

    @Singleton
    @Provides
    QuoteDataSource webDataSource(ApplicationController applicationController) {
        return new WebDataSourceImpl(applicationController);
    }

    @Singleton
    @Provides
    LocalDataSource cacheDataSource(ApplicationController applicationController) {
        return new CacheDataSourceImpl(applicationController);
    }

    @Singleton
    @Provides
    QuoteDataRepository quoteDataRepository(QuoteDataSource quoteDataSource, LocalDataSource localDataSource) {
        return new QuoteDataRepositoryImpl(quoteDataSource, localDataSource);
    }

    @Singleton
    @Provides
    RequestQuoteUseCase requestQuoteUseCase(QuoteDataRepository quoteDataRepository, @Named("SUBSCRIBE") Scheduler subscribeOn, @Named("OBSERVE") Scheduler observeOn) {
        return new RequestQuoteUseCaseImpl(quoteDataRepository, subscribeOn, observeOn);
    }

    @Provides
    @Named("OBSERVE")
    Scheduler observeOnScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Named("SUBSCRIBE")
    Scheduler subscribeOnScheduler() {
        return Schedulers.io();
    }

    @Singleton
    @Provides
    Presenter presenter(RequestQuoteUseCase requestQuoteUseCase) {
        return new MainPresenter(requestQuoteUseCase);
    }
}
