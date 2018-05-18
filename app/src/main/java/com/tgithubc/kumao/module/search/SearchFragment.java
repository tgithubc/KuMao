package com.tgithubc.kumao.module.search;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tgithubc.kumao.R;
import com.tgithubc.kumao.base.BaseFragment;
import com.tgithubc.kumao.widget.TagLayout;

import java.util.List;

/**
 * Created by tc :)
 */
public class SearchFragment extends BaseFragment implements ISearchContract.V {

    private SearchPresenter mPresenter;
    private TagLayout mHotWordLayout;
    private TextView mHotWordErrorTip;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SearchPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void init(View view, LayoutInflater inflater, Bundle savedInstanceState) {
        mHotWordLayout = view.findViewById(R.id.search_hotword_layout);
        mHotWordErrorTip = view.findViewById(R.id.search_hotword_error_tip);
        mPresenter.getHotWord();
    }

    @Override
    protected View onCreateTitleView(LayoutInflater inflater, FrameLayout titleContainer) {
        View titleBar = inflater.inflate(R.layout.titlebar_search, titleContainer, false);
        return titleBar;
    }

    @Override
    public void showHotWord(List<String> hotword) {
        if (hotword != null && !hotword.isEmpty()) {
            mHotWordLayout.setTags(hotword);
        } else {
            mHotWordErrorTip.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showHotWordErrorTip() {
        mHotWordLayout.setVisibility(View.GONE);
        mHotWordErrorTip.setVisibility(View.VISIBLE);
    }
}
