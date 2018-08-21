package com.tgithubc.kumao.module.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.tgithubc.kumao.R;
import com.tgithubc.kumao.base.BaseFragment;

/**
 * Created by tc :)
 */
public class MineFragment extends BaseFragment  {

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    public void init(View view, LayoutInflater inflater, Bundle savedInstanceState) {

    }
}
