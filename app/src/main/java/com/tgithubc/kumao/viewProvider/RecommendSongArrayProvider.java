package com.tgithubc.kumao.viewProvider;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tgithubc.fresco_wapper.ImageLoaderWrapper;
import com.tgithubc.fresco_wapper.config.ImageLoadConfig;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.RecommendSongArray;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.constant.Constant;
import com.tgithubc.kumao.widget.NoScrollRecyclerView;

import java.util.List;

/**
 * Created by tc :)
 */
public class RecommendSongArrayProvider extends BaseItemProvider<RecommendSongArray, BaseViewHolder> {

    private Context mContext;
    private ImageLoadConfig mConfig;

    public RecommendSongArrayProvider(Context context) {
        this.mContext = context;
        this.mConfig = new ImageLoadConfig.Builder(context)
                .setLoadingDrawable(R.drawable.pic_loading)
                .setFailureDrawable(R.drawable.pic_loading)
                .roundedCorner(8)
                .create();
    }

    @Override
    public int viewType() {
        return Constant.UIType.TYPE_RECOMMEND_SONG;
    }

    @Override
    public int layout() {
        return R.layout.rv_item_recommend_song_array;
    }

    @Override
    public void convert(BaseViewHolder helper, RecommendSongArray songArray, int position) {
        NoScrollRecyclerView recyclerView = helper.getView(R.id.no_scroll_recyclerview);
        List<Song> songs = songArray.getSongs();
        SongAdapter adapter = new SongAdapter(songs);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);
    }

    private class SongAdapter extends BaseQuickAdapter<Song, BaseViewHolder> {

        public SongAdapter(@Nullable List<Song> data) {
            super(R.layout.rv_item_recommend_song, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Song song) {
            SimpleDraweeView imgView = helper.getView(R.id.song_pic);
            ImageLoaderWrapper.getInstance().load(imgView, song.getSmallPic(), mConfig);
            helper.setText(R.id.song_name, song.getSongName());
            helper.setText(R.id.song_artist, song.getAuthorName());
            helper.setText(R.id.song_album, song.getAlbumName());
        }
    }
}

