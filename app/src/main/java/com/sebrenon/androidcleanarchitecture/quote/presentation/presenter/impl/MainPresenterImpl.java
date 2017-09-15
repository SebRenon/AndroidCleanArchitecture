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

package com.sebrenon.androidcleanarchitecture.quote.presentation.presenter.impl;

import com.sebrenon.androidcleanarchitecture.quote.domain.exception.InvalidSymbolFormat;
import com.sebrenon.androidcleanarchitecture.quote.domain.exception.QuoteNotFoundException;
import com.sebrenon.androidcleanarchitecture.quote.domain.interactor.RequestQuoteUseCase;
import com.sebrenon.androidcleanarchitecture.quote.presentation.presenter.Presenter;
import com.sebrenon.androidcleanarchitecture.quote.presentation.view.View;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Seb on 14/09/2017.
 */

public class MainPresenterImpl implements Presenter {

    @Inject
    RequestQuoteUseCase mUseCase;

    @Nullable
    private Disposable mDisposable;

    private View mView;

    @Inject
    public MainPresenterImpl(RequestQuoteUseCase useCase) {
        this.mUseCase = useCase;
    }

    @Override
    public void attachView(View view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        this.mView = null;
    }

    @Override
    public void searchButtonClicked(@Nonnull String value) {
        getQuoteData(value);
    }

    private void getQuoteData(@Nonnull String value) {
        this.mView.showLoader();
        this.mUseCase.getQuote(value).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                if (MainPresenterImpl.this.mDisposable != null) {
                    MainPresenterImpl.this.mDisposable.dispose();
                }
                MainPresenterImpl.this.mDisposable = d;
            }

            @Override
            public void onNext(String s) {
                MainPresenterImpl.this.mView.hideLoader();
                MainPresenterImpl.this.mView.showQuote(s);
            }

            @Override
            public void onError(Throwable e) {
                MainPresenterImpl.this.mView.hideLoader();
                String msg = "Error, please try again.";
                if (e instanceof QuoteNotFoundException) {
                    msg = "Quote not found for this symbol.";
                } else if (e instanceof InvalidSymbolFormat) {
                    msg = "Invalid symbol.";
                }
                MainPresenterImpl.this.mView.showError(msg);
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void restoreData(@Nonnull String input) {
        if (!input.isEmpty()) {
            getQuoteData(input);
        }
    }
}
