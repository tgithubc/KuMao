package com.tgithubc.kumao.module.mv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.tgithubc.kumao.R;
import com.tgithubc.kumao.base.BaseFragment;

/**
 * Created by tc :)
 */
public class MvFragment extends BaseFragment  {

    public static MvFragment newInstance() {
        return new MvFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    public void init(View view, LayoutInflater inflater, Bundle savedInstanceState) {

    }
}
