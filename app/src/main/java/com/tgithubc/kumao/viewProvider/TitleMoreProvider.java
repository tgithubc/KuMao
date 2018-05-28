package com.tgithubc.kumao.viewProvider;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.BaseData;

/**
 * Created by tc :)
 */
public class TitleMoreProvider extends BaseItemProvider<BaseData, BaseViewHolder> {

    @Override
    public int viewType() {
        return BaseData.TYPE_TITLE_MORE;
    }

    @Override
    public int layout() {
        return R.layout.rv_item_title_more;
    }

    @Override
    public void convert(BaseViewHolder helper, BaseData data, int position) {
        helper.setText(R.id.title, (String) data.getData());
        helper.addOnClickListener(R.id.title_see_more);
    }
}
