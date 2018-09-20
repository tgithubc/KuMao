package com.tgithubc.kumao.viewProvider;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tgithubc.fresco_wapper.ImageLoaderWrapper;
import com.tgithubc.fresco_wapper.config.ImageLoadConfig;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.Radio;
import com.tgithubc.kumao.bean.RadioArray;
import com.tgithubc.kumao.constant.Constant;

import java.util.List;


/**
 * Created by tc :)
 */
public class RadioArray3SProvider extends BaseItemProvider<RadioArray, BaseViewHolder> {

    private Context mContext;
    private ImageLoadConfig mConfig;

    public RadioArray3SProvider(Context context) {
        this.mContext = context;
        this.mConfig = new ImageLoadConfig.Builder(context)
                .setLoadingDrawable(R.drawable.pic_loading)
                .setFailureDrawable(R.drawable.pic_loading)
                .circle()
                .create();
    }

    @Override
    public int viewType() {
        return Constant.UIType.TYPE_RADIO_3S;
    }

    @Override
    public int layout() {
        return R.layout.rv_item_3s_container;
    }

    @Override
    public void convert(BaseViewHolder helper, RadioArray arrary, int pos) {
        RecyclerView recyclerView = helper.getView(R.id.square_3s_view);
        List<Radio> radios = arrary.getRadioList();
        if (radios != null && !radios.isEmpty()) {
            radios = radios.subList(0, 6);
            RadioArrayAdapter adapter = new RadioArrayAdapter(radios);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);
        }
    }

    private class RadioArrayAdapter extends BaseQuickAdapter<Radio, BaseViewHolder> {

        RadioArrayAdapter(@Nullable List<Radio> data) {
            super(R.layout.rv_item_3s_circle_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Radio item) {
            SimpleDraweeView imgView = helper.getView(R.id.radio_pic);
            ImageLoaderWrapper.getInstance().load(imgView, item.getPic(), mConfig);
            helper.setText(R.id.radio_name, item.getName());
        }
    }
}
