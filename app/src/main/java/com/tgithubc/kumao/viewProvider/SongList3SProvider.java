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
import com.tgithubc.fresco_wapper.config.ImageLoadConfig;
import com.tgithubc.fresco_wapper.listener.IDisplayImageListener;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.SongListArray;
import com.tgithubc.kumao.bean.SongList;
import com.tgithubc.kumao.constant.Constant;
import com.tgithubc.kumao.fragment.FragmentOperation;
import com.tgithubc.kumao.module.detailpage.songlist.SongListFragment;

import java.util.List;


/**
 * Created by tc :)
 */
public class SongList3SProvider extends BaseItemProvider<SongListArray, BaseViewHolder> {

    private Context mContext;
    private ImageLoadConfig mConfig;

    public SongList3SProvider(Context context) {
        this.mContext = context;
        this.mConfig = new ImageLoadConfig.Builder(context)
                .setLoadingDrawable(R.drawable.pic_loading)
                .setFailureDrawable(R.drawable.pic_loading)
                .roundedCorner(8)
                .create();
    }

    @Override
    public int viewType() {
        return Constant.UIType.TYPE_SONG_LIST_3S;
    }

    @Override
    public int layout() {
        return R.layout.rv_item_3s_square;
    }

    @Override
    public void convert(BaseViewHolder helper, SongListArray arrary, int pos) {
        RecyclerView recyclerView = helper.getView(R.id.square_3s_view);
        List<SongList> songLists = arrary.getSongLists();
        SongListAdapter adapter = new SongListAdapter(songLists);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    private class SongListAdapter extends BaseQuickAdapter<SongList, BaseViewHolder> {

        SongListAdapter(@Nullable List<SongList> data) {
            super(R.layout.rv_item_3s_square_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SongList item) {
            SimpleDraweeView imgView = helper.getView(R.id.song_list_pic);
            ImageLoaderWrapper.getInstance().load(imgView, item.getPic(), mConfig,
                    new IDisplayImageListener<ImageInfo>() {
                        @Override
                        public void onSuccess(ImageInfo result, Animatable animatable) {
                            resetImageViewWH(result, imgView);
                            helper.setText(R.id.song_list_name, item.getName());
                        }

                        @Override
                        public void onFailure(Throwable throwable) {
                            helper.setText(R.id.song_list_name, item.getName());
                        }
                    }
            );
            helper.itemView.setOnClickListener(v -> {
                SongListFragment fragment = SongListFragment.newInstance(item);
                FragmentOperation.getInstance().showSubFragment(fragment);
            });
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
