package com.tgithubc.kumao.module.detailpage.base.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.module.detailpage.base.DetailPageBaseFragment;
import com.tgithubc.kumao.module.detailpage.base.SongAdapter;

import java.util.List;

/**
 * Created by tc :)
 */
public abstract class DetailListPageFragment extends DetailPageBaseFragment implements IDetailListPageContract.V {

    private RecyclerView mRecyclerView;
    private SongAdapter mSongAdapter;
    private DetailListPagePresenter mPresenter;
    private FrameLayout mLoadingView;
    private View mRootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView(this);
    }

    protected abstract DetailListPagePresenter createPresenter();

    @Override
    public View onCreateContentView(LayoutInflater inflater, FrameLayout contentContainer) {
        View view = inflater.inflate(R.layout.detail_page_content_list, contentContainer, true);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLoadingView = view.findViewById(R.id.loading_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRootView = view;
        return view;
    }

    @Override
    public View onCreateHeadView(LayoutInflater inflater, FrameLayout headContainer) {
        return null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSongAdapter = new SongAdapter(null);
        mSongAdapter.bindToRecyclerView(mRecyclerView);
        if (DetailPageBaseFragment.TYPE_LIST_SONGLIST != getType()) {
            mSongAdapter.setOnLoadMoreListener(() -> mPresenter.loadMore(), mRecyclerView);
        }
        mPresenter.getSongList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public View getTargetView() {
        return mRecyclerView;
    }

    @Override
    public void showSongList(List<Song> songList) {
        mLoadingView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mSongAdapter.setNewData(songList);
    }

    @Override
    public void showLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        mLoadingView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.detail_page_empty_content,
                (ViewGroup) mRootView, false);
        mSongAdapter.setEmptyView(view);
    }

    @Override
    public void loadMoreFinish() {
        mSongAdapter.loadMoreEnd(true);
    }

    @Override
    public void loadMoreRefresh(List<Song> more) {
        mSongAdapter.addData(more);
        mSongAdapter.loadMoreComplete();
    }

    @Override
    public void loadMoreError() {
        mSongAdapter.loadMoreFail();
    }
}
