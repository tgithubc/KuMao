package com.tgithubc.kumao.module.search;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.KeyWord;

import java.util.List;

/**
 * Created by tc :)
 */

public class SearchHistoryAdapter extends BaseQuickAdapter<KeyWord, BaseViewHolder> {

    public SearchHistoryAdapter(@Nullable List<KeyWord> data) {
        super(R.layout.rv_item_search_history, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KeyWord item) {
        helper.setText(R.id.search_history_key_word, item.getKeyWord());
    }
}
