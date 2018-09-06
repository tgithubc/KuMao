package com.tgithubc.kumao.viewProvider;


import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.HotSongListArrary;

/**
 * Created by tc :)
 */
public class HotSongListProvider extends BaseItemProvider<HotSongListArrary, BaseViewHolder> {

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
    }


}
