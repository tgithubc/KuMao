package com.tgithubc.kumao.viewProvider;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.tgithubc.fresco_wapper.ImageLoaderWrapper;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.fragment.FragmentOperation;
import com.tgithubc.kumao.module.listpage.billboard.BillboardFragment;

import java.util.List;

/**
 * Created by tc :)
 */
public class BillboardProvider extends BaseItemProvider<BaseData, BaseViewHolder> {

    @Override
    public int viewType() {
        return BaseData.TYPE_RANK_BILLBOARD;
    }

    @Override
    public int layout() {
        return R.layout.rv_item_billboard;
    }

    @Override
    public void convert(BaseViewHolder helper, BaseData data, int position) {
        if (data.getData() instanceof Billboard) {
            Billboard.Info info = ((Billboard) data.getData()).getBillboardInfo();
            if (info != null) {
                helper.setText(R.id.billboard_title, info.getName());
                ImageLoaderWrapper.getInstance().load(helper.getView(R.id.billboard_pic), info.getPic_s192());
            }
            List<Song> songs = ((Billboard) data.getData()).getSongList();
            if (songs != null && songs.size() >= 3) {
                helper.setText(R.id.billboard_song_title_1, "1." + songs.get(0).getSongName());
                helper.setText(R.id.billboard_song_title_2, "2." + songs.get(1).getSongName());
                helper.setText(R.id.billboard_song_title_3, "3." + songs.get(2).getSongName());
            }
        }
    }

    @Override
    public void onClick(BaseViewHolder helper, BaseData data, int position) {
        BillboardFragment fragment = BillboardFragment.newInstance(data);
        FragmentOperation.getInstance().showSubFragment(fragment);
    }
}
