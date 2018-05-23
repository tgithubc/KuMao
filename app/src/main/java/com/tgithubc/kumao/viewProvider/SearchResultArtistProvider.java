package com.tgithubc.kumao.viewProvider;

import android.content.Context;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.tgithubc.fresco_wapper.ImageLoaderWrapper;
import com.tgithubc.fresco_wapper.config.ImageLoadConfig;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.Artist;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.Billboard;

/**
 * Created by tc :)
 */
public class SearchResultArtistProvider extends BaseItemProvider<BaseData, BaseViewHolder> {

    private ImageLoadConfig mConfig;

    public SearchResultArtistProvider(Context context) {
        mConfig = new ImageLoadConfig.Builder(context).circle().create();
    }

    @Override
    public int viewType() {
        return BaseData.TYPE_SEARCH_RESULT_ARTIST;
    }

    @Override
    public int layout() {
        return R.layout.rv_item_search_result_artist;
    }

    @Override
    public void convert(BaseViewHolder helper, BaseData data, int position) {
        Artist artist = (Artist) data.getData();
        helper.setText(R.id.search_result_artist_number, "单曲 " + artist.getSongCount() + " 专辑 " + artist.getAlbumCount());
        helper.setText(R.id.search_result_artist_name, artist.getName());
        ImageLoaderWrapper.getInstance().load(helper.getView(R.id.search_result_artist_pic), artist.getPic(), mConfig);
    }
}
