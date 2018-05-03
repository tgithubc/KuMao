package com.tgithubc.kumao.module.featured;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.tgithubc.kumao.R;
import com.tgithubc.kumao.base.BaseFragment;

/**
 * Created by tc :)
 */
public class FeaturedFragment extends BaseFragment implements IFeaturedContract.V {

    private FeaturedPresenter mPresenter;

    public static FeaturedFragment newInstance() {
        return new FeaturedFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new FeaturedPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void init(View view, LayoutInflater inflater, Bundle savedInstanceState) {
        mPresenter.getFeaturedData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_featured;
    }

    @Override
    public boolean isShowLCEE() {
        return true;
    }

    @Override
    public void showEmpty() {
        baseShowEmpty();
    }

    @Override
    public void showError() {
        baseShowError();
    }

    @Override
    public void showLoading() {
        baseShowLoading();
    }

    @Override
    public void showContent() {
        baseShowContent();
    }
}
