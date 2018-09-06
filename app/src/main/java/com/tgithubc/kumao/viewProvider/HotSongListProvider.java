package com.tgithubc.kumao.viewProvider;


import android.content.Context;
import android.graphics.drawable.Animatable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.tgithubc.fresco_wapper.ImageLoaderWrapper;
import com.tgithubc.fresco_wapper.listener.IDisplayImageListener;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.HotSongListArrary;
import com.tgithubc.kumao.bean.SongList;

import java.util.List;


/**
 * Created by tc :)
 */
public class HotSongListProvider extends BaseItemProvider<HotSongListArrary, BaseViewHolder> {

    private Context mContext;

    public HotSongListProvider(Context context) {
        this.mContext = context;
    }

    @Override
    public int viewType() {
        return BaseData.TYPE_HOT_SONG_LIST;
    }

    @Override
    public int layout() {
        return R.layout.rv_item_3s_square;
    }

    @Override
    public void convert(BaseViewHolder helper, HotSongListArrary arrary, int pos) {
        RecyclerView recyclerView = helper.getView(R.id.square_3s_view);
        List<SongList> songLists = arrary.getSongLists();
        HotSongListAdapter adapter = new HotSongListAdapter(songLists);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    private class HotSongListAdapter extends BaseQuickAdapter<SongList, BaseViewHolder> {

        HotSongListAdapter(@Nullable List<SongList> data) {
            super(R.layout.rv_item_3s_square_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SongList item) {
            SimpleDraweeView imgView = helper.getView(R.id.song_list_pic);
            helper.setText(R.id.song_list_name, item.getName());
            ImageLoaderWrapper.getInstance().load(imgView, item.getPic(),
                    new IDisplayImageListener<ImageInfo>() {
                        @Override
                        public void onSuccess(ImageInfo result, Animatable animatable) {
                            resetImageViewWH(result, imgView);
                        }

                        @Override
                        public void onFailure(Throwable throwable) {

                        }
                    }
            );
        }

        private void resetImageViewWH(ImageInfo result, SimpleDraweeView imgView) {
            int width = result.getWidth();
            int height = result.getHeight();
            float ratio = (width * 1f) / height;
            int imageViewWidth = imgView.getWidth();
            int imageViewHeight = (int) (imageViewWidth / ratio);
            ViewGroup.LayoutParams layoutParams = imgView.getLayoutParams();
            layoutParams.width = imageViewWidth;
            layoutParams.height = imageViewHeight;
            imgView.setLayoutParams(layoutParams);
        }
    }
}
