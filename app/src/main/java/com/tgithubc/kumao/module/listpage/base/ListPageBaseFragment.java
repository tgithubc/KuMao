package com.tgithubc.kumao.module.listpage.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.tgithubc.kumao.R;
import com.tgithubc.kumao.base.BaseFragment;
import com.tgithubc.kumao.widget.dragLayout.StickyHeaderLayout;

/**
 * 歌单，榜单，专辑，歌手的父Fragment
 * Created by tc :)
 */
public abstract class ListPageBaseFragment extends BaseFragment {

    // 基本的id，请求用的，不能为空
    protected static final String KEY_LIST_ID = "key_list_id";
    // 基本的的名字
    protected static final String KEY_LIST_NAME = "key_list_name";
    // 基本的图片地址
    protected static final String KEY_LIST_PIC = "key_list_pic";

    private StickyHeaderLayout mStickyHeaderLayout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_list_page;
    }

    @Override
    public void init(View view, LayoutInflater inflater, Bundle savedInstanceState) {
        mStickyHeaderLayout = view.findViewById(R.id.sticky_header_layout);
        FrameLayout headContainer = view.findViewById(R.id.list_page_head);
        FrameLayout contentContainer = view.findViewById(R.id.list_page_content);
        View head = onCreateHeadView(inflater, headContainer);
        View content = onCreateContentView(inflater, contentContainer);
        // attachToRoot false 不想增加多一层嵌套
        mStickyHeaderLayout.addView(new View[]{head, content});
    }

    @Override
    protected View onCreateTitleView(LayoutInflater inflater, FrameLayout titleContainer) {
        return null;
    }

    public abstract View onCreateContentView(LayoutInflater inflater, FrameLayout contentContainer);

    public abstract View onCreateHeadView(LayoutInflater inflater, FrameLayout headContainer);

    @Override
    public boolean isShowLCEE() {
        return true;
    }
}
