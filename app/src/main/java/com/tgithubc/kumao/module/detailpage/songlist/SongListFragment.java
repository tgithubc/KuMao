package com.tgithubc.kumao.module.detailpage.songlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.SongList;
import com.tgithubc.kumao.module.detailpage.base.list.DetailListPageFragment;
import com.tgithubc.kumao.module.detailpage.base.list.DetailListPagePresenter;
import com.tgithubc.kumao.module.detailpage.base.list.IDetailListPageContract;
import com.tgithubc.kumao.util.RxMap;

/**
 * 榜单
 * Created by tc :)
 */
public class SongListFragment extends DetailListPageFragment implements IDetailListPageContract.V {

    public static SongListFragment newInstance(SongList data) {
        SongListFragment fragment = new SongListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_LIST_ID, data.getSongListId());
        bundle.putString(KEY_LIST_PIC, data.getPic());
        bundle.putString(KEY_LIST_NAME, data.getName());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected DetailListPagePresenter createPresenter() {
        return new DetailListPagePresenter(TYPE_LIST_SONGLIST, new RxMap<String, String>()
                .put("listid", mId)
                .build());
    }

    @Override
    public View onCreateHeadView(LayoutInflater inflater, FrameLayout headContainer) {
        View view = inflater.inflate(R.layout.detail_page_header_billboard, headContainer, true);
        return view;
    }

    @Override
    public int getType() {
        return TYPE_LIST_SONGLIST;
    }
}
