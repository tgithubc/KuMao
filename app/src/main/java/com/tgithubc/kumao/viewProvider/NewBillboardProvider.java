package com.tgithubc.kumao.viewProvider;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.tgithubc.fresco_wapper.ImageLoaderWrapper;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.bean.Song;

import java.util.List;

/**
 * Created by tc :)
 */
public class NewBillboardProvider extends BaseItemProvider<Billboard, BaseViewHolder> {

    @Override
    public int viewType() {
        return BaseData.TYPE_RANK_NEW_BILLBOARD;
    }

    @Override
    public int layout() {
        return R.layout.rv_item_new_billboard;
    }

    @Override
    public void convert(BaseViewHolder helper, Billboard data, int position) {
        Billboard.Info info = data.getBillboardInfo();
        if (info != null) {
            helper.setText(R.id.billboard_desc, info.getComment());
        }
        List<Song> songs = data.getSongList();
        if (songs != null && songs.size() >= 3) {
            helper.setText(R.id.billboard_song_name1, songs.get(0).getSongName());
            helper.setText(R.id.billboard_song_name2, songs.get(1).getSongName());
            helper.setText(R.id.billboard_song_name3, songs.get(2).getSongName());
            helper.setText(R.id.billboard_artist_name1, songs.get(0).getAuthorName());
            helper.setText(R.id.billboard_artist_name2, songs.get(1).getAuthorName());
            helper.setText(R.id.billboard_artist_name3, songs.get(2).getAuthorName());
            ImageLoaderWrapper.getInstance().load(helper.getView(R.id.billboard_pic1), songs.get(0).getBigPic());
            ImageLoaderWrapper.getInstance().load(helper.getView(R.id.billboard_pic2), songs.get(1).getBigPic());
            ImageLoaderWrapper.getInstance().load(helper.getView(R.id.billboard_pic3), songs.get(2).getBigPic());
        }
    }
}
