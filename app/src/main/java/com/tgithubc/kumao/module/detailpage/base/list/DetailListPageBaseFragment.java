package com.tgithubc.kumao.module.detailpage.base.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.tgithubc.kumao.R;
import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.module.detailpage.base.DetailPageBaseFragment;
import com.tgithubc.kumao.module.detailpage.base.SongAdapter;

import java.util.List;

/**
 * Created by tc :)
 */
public abstract class DetailListPageBaseFragment extends DetailPageBaseFragment implements IDetailListPageContract.V {

    public static final int KEY_LIST_BILLBOARD = 1;

    private RecyclerView mRecyclerView;
    private SongAdapter mSongAdapter;
    private DetailListPagePresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new DetailListPagePresenter();
        mPresenter.attachView(this);
    }

    @Override
    public View onCreateContentView(LayoutInflater inflater, FrameLayout contentContainer) {
        View view = inflater.inflate(R.layout.detail_page_content_list, contentContainer, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
        // 统一请求
        mPresenter.getSongList(getType(), getRequestValue());
    }

    public abstract int getType();

    public abstract Task.RequestValue getRequestValue();

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
        mSongAdapter.setNewData(songList);
    }
}
