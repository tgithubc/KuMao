package com.tgithubc.kumao.viewProvider;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.Title;
import com.tgithubc.kumao.constant.Constant;

/**
 * Created by tc :)
 */
public class TitleMoreProvider extends BaseItemProvider<Title, BaseViewHolder> {

    @Override
    public int viewType() {
        return Constant.UIType.TYPE_TITLE_MORE;
    }

    @Override
    public int layout() {
        return R.layout.rv_item_title_more;
    }

    @Override
    public void convert(BaseViewHolder helper, Title title, int position) {
        helper.setText(R.id.title, title.getTitle());
        helper.addOnClickListener(R.id.title_see_more);
    }
}
