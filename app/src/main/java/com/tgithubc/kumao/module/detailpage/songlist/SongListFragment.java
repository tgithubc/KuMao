package com.tgithubc.kumao.module.detailpage.songlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tgithubc.fresco_wapper.ImageLoaderWrapper;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.SongList;
import com.tgithubc.kumao.module.detailpage.base.list.DetailListPageFragment;
import com.tgithubc.kumao.module.detailpage.base.list.DetailListPagePresenter;
import com.tgithubc.kumao.module.detailpage.base.list.IDetailListPageContract;
import com.tgithubc.kumao.util.RxMap;
import com.tgithubc.kumao.widget.TagLayout;

/**
 * 歌单
 * Created by tc :)
 */
public class SongListFragment extends DetailListPageFragment implements IDetailListPageContract.V {

    // 歌单的收听数
    private static final String KEY_LISTEN_NUMB = "key_Listen_numb";
    private static final String KEY_TAG = "key_tag";

    private String mListenNumb;
    private String[] mSongListTag;

    public static SongListFragment newInstance(SongList data) {
        SongListFragment fragment = new SongListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_LIST_ID, data.getSongListId());
        bundle.putString(KEY_LIST_PIC, data.getPic());
        bundle.putString(KEY_LIST_NAME, data.getName());
        bundle.putString(KEY_LISTEN_NUMB, data.getListenNum());
        bundle.putStringArray(KEY_TAG, data.getTags());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mListenNumb = bundle.getString(KEY_LISTEN_NUMB);
            mSongListTag = bundle.getStringArray(KEY_TAG);
        }
    }

    @Override
    protected DetailListPagePresenter createPresenter() {
        return new DetailListPagePresenter(TYPE_LIST_SONGLIST,
                new RxMap<String, String>()
                        .put("listid", mId)
                        .build());
    }

    @Override
    public View onCreateHeadView(LayoutInflater inflater, FrameLayout headContainer) {
        View view = inflater.inflate(R.layout.detail_page_header_songlist, headContainer, true);
        SimpleDraweeView imgView = view.findViewById(R.id.songlist_pic);
        ImageLoaderWrapper.getInstance().load(imgView, mPicUrl);
        TextView numbTv = view.findViewById(R.id.songlist_listen_num);
        numbTv.setText(mListenNumb);
        TextView nameTv = view.findViewById(R.id.songlist_name);
        nameTv.setText(mName);
        TagLayout tagLayout = view.findViewById(R.id.songlist_tag);
        tagLayout.setTags(mSongListTag);
        return view;
    }

    @Override
    public int getType() {
        return TYPE_LIST_SONGLIST;
    }
}
