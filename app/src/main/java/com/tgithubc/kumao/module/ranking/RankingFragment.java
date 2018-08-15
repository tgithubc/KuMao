package com.tgithubc.kumao.module.ranking;

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
public class RankingFragment extends BaseFragment implements IRankingContract.V {

    private RecyclerView mRecyclerView;
    private RankingPresenter mPresenter;
    private RankingAdapter mAdapter;

    public static RankingFragment newInstance() {
        return new RankingFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ranking;
    }

    @Override
    public void init(View view, LayoutInflater inflater, Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RankingAdapter(null);
        mAdapter.bindToRecyclerView(mRecyclerView);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new RankingPresenter();
        mPresenter.attachView(this);
        mPresenter.getRankingData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public boolean isShowLCEE() {
        return true;
    }

    @Override
    public void showRankingView(List<BaseData> result) {
        mAdapter.setNewData(result);
    }
}
