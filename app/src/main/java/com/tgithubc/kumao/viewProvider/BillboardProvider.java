package com.tgithubc.kumao.viewProvider;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.bean.BaseData;

/**
 * Created by tc :)
 */
public class BillboardProvider extends BaseItemProvider<BaseData, BaseViewHolder> {

    @Override
    public int viewType() {
        return BaseData.TYPE_BILLBOARD;
    }

    @Override
    public int layout() {
        return R.layout.rv_item_billboard;
    }

    @Override
    public void convert(BaseViewHolder helper, BaseData data, int position) {
        if (data.getData() instanceof Billboard) {

        }
    }
}
