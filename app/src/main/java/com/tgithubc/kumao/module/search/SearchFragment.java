package com.tgithubc.kumao.module.search;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.tgithubc.kumao.R;
import com.tgithubc.kumao.base.BaseFragment;

/**
 * Created by tc :)
 */
public class SearchFragment extends BaseFragment {

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void init(View view, LayoutInflater inflater, Bundle savedInstanceState) {

    }

    @Override
    protected View onCreateTitleView(LayoutInflater inflater, FrameLayout titleContainer) {
        View titleBar = inflater.inflate(R.layout.titlebar_search, titleContainer, false);
        return titleBar;
    }
}
