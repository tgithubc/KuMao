package com.tgithubc.kumao.viewProvider;


import android.text.TextUtils;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.tgithubc.fresco_wapper.ImageLoaderWrapper;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.Album;
import com.tgithubc.kumao.bean.BaseData;

/**
 * Created by tc :)
 */
public class SearchResultAlbumProvider extends BaseItemProvider<Album, BaseViewHolder> {

    @Override
    public int viewType() {
        return BaseData.TYPE_SEARCH_RESULT_ALBUM;
    }

    @Override
    public int layout() {
        return R.layout.rv_item_search_result_album;
    }

    @Override
    public void convert(BaseViewHolder helper, Album album, int position) {
        helper.setText(R.id.search_result_album_publish_time,
                "发行时间：" + (TextUtils.isEmpty(album.getPublishTime()) ? "未知" : album.getPublishTime()));
        helper.setText(R.id.search_result_album_name, album.getName());
        ImageLoaderWrapper.getInstance().load(helper.getView(R.id.search_result_album_pic), album.getPic());
    }
}
