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

package com.sebrenon.androidcleanarchitecture.presentation.presenter.impl;

import com.sebrenon.androidcleanarchitecture.domain.exception.InvalidSymbolFormat;
import com.sebrenon.androidcleanarchitecture.domain.exception.QuoteNotFoundException;
import com.sebrenon.androidcleanarchitecture.domain.interactor.RequestQuoteUseCase;
import com.sebrenon.androidcleanarchitecture.presentation.presenter.Presenter;
import com.sebrenon.androidcleanarchitecture.presentation.view.View;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Seb on 14/09/2017.
 */

public class MainPresenter implements Presenter {

    @Nonnull
    private final RequestQuoteUseCase mUseCase;

    @Nullable
    private Disposable mDisposable;

    private View mView;

    public MainPresenter(@Nonnull RequestQuoteUseCase useCase) {
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
                if (MainPresenter.this.mDisposable != null) {
                    MainPresenter.this.mDisposable.dispose();
                }
                MainPresenter.this.mDisposable = d;
            }

            @Override
            public void onNext(String s) {
                MainPresenter.this.mView.hideLoader();
                MainPresenter.this.mView.showQuote(s);
            }

            @Override
            public void onError(Throwable e) {
                MainPresenter.this.mView.hideLoader();
                String msg = "Error, please try again.";
                if (e instanceof QuoteNotFoundException) {
                    msg = "Quote not found for this symbol.";
                } else if (e instanceof InvalidSymbolFormat) {
                    msg = "Invalid symbol.";
                }
                MainPresenter.this.mView.showError(msg);
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
