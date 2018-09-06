package com.tgithubc.kumao.module.featured;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.tgithubc.kumao.R;
import com.tgithubc.kumao.base.BaseFragment;
import com.tgithubc.kumao.bean.BaseData;

import java.util.List;

/**
 * Created by tc :)
 */
public class FeaturedFragment extends BaseFragment implements IFeaturedContract.V {

    private FeaturedPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private FeaturedAdapter mAdapter;

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
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void init(View view, LayoutInflater inflater, Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new FeaturedAdapter(getActivity(), null);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mPresenter.getFeaturedData();
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
    public void showFeatureView(List<BaseData> mFeedData) {
        mAdapter.setNewData(mFeedData);
    }
}
