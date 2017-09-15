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

import com.sebrenon.androidcleanarchitecture.R;
import com.sebrenon.androidcleanarchitecture.application.impl.ApplicationControllerImpl;
import com.sebrenon.androidcleanarchitecture.data.repository.impl.QuoteDataRepositoryImpl;
import com.sebrenon.androidcleanarchitecture.domain.interactor.impl.RequestQuoteUseCaseImpl;
import com.sebrenon.androidcleanarchitecture.network.datasource.impl.WebDataSourceImpl;
import com.sebrenon.androidcleanarchitecture.presentation.presenter.Presenter;
import com.sebrenon.androidcleanarchitecture.presentation.presenter.impl.MainPresenter;
import com.sebrenon.androidcleanarchitecture.presentation.view.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.annotation.Nonnull;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View {

    static final ButterKnife.Action<android.view.View> HIDE = new ButterKnife.Action<android.view.View>() {
        @Override
        public void apply(@NonNull android.view.View view, int index) {
            view.setVisibility(android.view.View.INVISIBLE);
        }
    };

    static final ButterKnife.Action<android.view.View> SHOW = new ButterKnife.Action<android.view.View>() {
        @Override
        public void apply(@NonNull android.view.View view, int index) {
            view.setVisibility(android.view.View.VISIBLE);
        }
    };

    Presenter mPresenter;

    @BindView(R.id.txt_result)
    TextView mTxtResult;

    @BindView(R.id.txt_error)
    TextView mTxtError;

    @BindView(R.id.etxt_main)
    EditText mEditText;

    @BindView(R.id.btn_main)
    Button mButton;

    @BindView(R.id.prg_main)
    ProgressBar mProgress;

    @BindViews({R.id.etxt_main, R.id.btn_main})
    List<android.view.View> mTopViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        mPresenter = new MainPresenter(
                new RequestQuoteUseCaseImpl(new QuoteDataRepositoryImpl(new WebDataSourceImpl(ApplicationControllerImpl.getInstance())), Schedulers.io(), AndroidSchedulers.mainThread()));
        mPresenter.attachView(MainActivity.this);
    }

    @OnClick(R.id.btn_main)
    public void submit(android.view.View view) {
        mPresenter.searchButtonClicked(mEditText.getText().toString());
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showLoader() {
        ButterKnife.apply(mTopViews, HIDE);
        mTxtError.setVisibility(android.view.View.INVISIBLE);
        mTxtResult.setVisibility(android.view.View.INVISIBLE);
        mProgress.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        mProgress.setVisibility(android.view.View.GONE);
    }

    @Override
    public void showError(@Nonnull String msg) {
        mProgress.setVisibility(android.view.View.GONE);
        ButterKnife.apply(mTopViews, SHOW);
        mTxtResult.setVisibility(android.view.View.INVISIBLE);
        mTxtError.setText(msg);
        mTxtError.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void showQuote(@Nonnull String quote) {
        ButterKnife.apply(mTopViews, SHOW);
        mTxtError.setVisibility(android.view.View.INVISIBLE);
        mTxtResult.setText(quote);
        mTxtResult.setVisibility(android.view.View.VISIBLE);
    }
}
