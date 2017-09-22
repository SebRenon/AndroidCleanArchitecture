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

package com.sebrenon.androidcleanarchitecture.quote.presentation.presenter;

import com.sebrenon.androidcleanarchitecture.quote.domain.exception.InvalidSymbolFormat;
import com.sebrenon.androidcleanarchitecture.quote.domain.exception.QuoteNotFoundException;
import com.sebrenon.androidcleanarchitecture.quote.domain.interactor.RequestQuoteUseCase;
import com.sebrenon.androidcleanarchitecture.quote.presentation.presenter.impl.MainPresenterImpl;
import com.sebrenon.androidcleanarchitecture.quote.presentation.view.View;

import org.junit.Test;

import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by Seb on 21/09/2017.
 */

public class MainPresenterImplTest {

    @Test
    public void restoreData_success() {
        RequestQuoteUseCase requestQuoteUseCase = mock(RequestQuoteUseCase.class);
        View view = mock(View.class);

        Presenter presenter = new MainPresenterImpl(requestQuoteUseCase);
        presenter.attachView(view);

        Observable<String> observable = Observable.just("my super quote");
        when(requestQuoteUseCase.getQuote(anyString())).thenReturn(observable);

        presenter.restoreData("mySymbol");

        verifySuccessfulFetchQuote(view, requestQuoteUseCase);
    }

    private void verifySuccessfulFetchQuote(View view, RequestQuoteUseCase requestQuoteUseCase) {

        verify(view).showLoader();
        verify(requestQuoteUseCase).getQuote("mySymbol");
        verifyNoMoreInteractions(requestQuoteUseCase);

        verify(view).hideLoader();
        verify(view).showQuote("my super quote");
        verifyNoMoreInteractions(view);
    }

    @Test
    public void restoreData_quoteNotFoundError() {
        RequestQuoteUseCase requestQuoteUseCase = mock(RequestQuoteUseCase.class);
        View view = mock(View.class);

        Observable<String> observable = Observable.error(new QuoteNotFoundException());

        when(requestQuoteUseCase.getQuote(anyString())).thenReturn(observable);

        Presenter presenter = new MainPresenterImpl(requestQuoteUseCase);
        presenter.attachView(view);

        presenter.restoreData("mySymbol");

        verify(view).showLoader();
        verify(requestQuoteUseCase).getQuote("mySymbol");
        verifyNoMoreInteractions(requestQuoteUseCase);

        verify(view).hideLoader();
        verify(view).showError("Quote not found for this symbol.");
        verifyNoMoreInteractions(view);

    }

    @Test
    public void restoreData_invalidSymbolFormat() {
        RequestQuoteUseCase requestQuoteUseCase = mock(RequestQuoteUseCase.class);
        View view = mock(View.class);

        Observable<String> observable = Observable.error(new InvalidSymbolFormat());

        when(requestQuoteUseCase.getQuote(anyString())).thenReturn(observable);

        Presenter presenter = new MainPresenterImpl(requestQuoteUseCase);
        presenter.attachView(view);

        presenter.restoreData("mySymbol");

        verify(view).showLoader();
        verify(requestQuoteUseCase).getQuote("mySymbol");
        verifyNoMoreInteractions(requestQuoteUseCase);

        verify(view).hideLoader();
        verify(view).showError("Invalid symbol.");
        verifyNoMoreInteractions(view);

    }

    @Test
    public void restoreData_error() {
        RequestQuoteUseCase requestQuoteUseCase = mock(RequestQuoteUseCase.class);
        View view = mock(View.class);

        Observable<String> observable = Observable.error(new NullPointerException());

        when(requestQuoteUseCase.getQuote(anyString())).thenReturn(observable);

        Presenter presenter = new MainPresenterImpl(requestQuoteUseCase);
        presenter.attachView(view);

        presenter.restoreData("mySymbol");

        verify(view).showLoader();
        verify(requestQuoteUseCase).getQuote("mySymbol");
        verifyNoMoreInteractions(requestQuoteUseCase);

        verify(view).hideLoader();
        verify(view).showError("Error, please try again.");
        verifyNoMoreInteractions(view);

    }

    @Test
    public void restoreData_badInput() {
        RequestQuoteUseCase requestQuoteUseCase = mock(RequestQuoteUseCase.class);
        View view = mock(View.class);
        Presenter presenter = new MainPresenterImpl(requestQuoteUseCase);
        presenter.attachView(view);

        presenter.restoreData("");

        verifyNoMoreInteractions(view);
        verifyNoMoreInteractions(requestQuoteUseCase);
    }

    @Test
    public void detachView_success() {
        RequestQuoteUseCase requestQuoteUseCase = mock(RequestQuoteUseCase.class);
        View view = mock(View.class);
        Presenter presenter = new MainPresenterImpl(requestQuoteUseCase);
        presenter.attachView(view);

        presenter.detachView();

        verifyNoMoreInteractions(view);
        verifyNoMoreInteractions(requestQuoteUseCase);
    }

    @Test
    public void searchButtonClicked_success() {
        RequestQuoteUseCase requestQuoteUseCase = mock(RequestQuoteUseCase.class);
        View view = mock(View.class);
        Presenter presenter = new MainPresenterImpl(requestQuoteUseCase);
        presenter.attachView(view);

        Observable<String> observable = Observable.just("my super quote");
        when(requestQuoteUseCase.getQuote(anyString())).thenReturn(observable);

        presenter.searchButtonClicked("mySymbol");

        verifySuccessfulFetchQuote(view, requestQuoteUseCase);

    }
}
