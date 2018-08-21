package com.tgithubc.kumao.module.detailpage.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tgithubc.fresco_wapper.ImageLoaderWrapper;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.base.BaseFragment;
import com.tgithubc.kumao.widget.TitleBar;
import com.tgithubc.kumao.widget.dragLayout.StickyHeaderLayout;

/**
 * 歌单，专辑，榜单，歌手父页面
 * 按页面具体样式分层
 * 顶部：单纯本地携带数据，展示样式不同
 * 底部可能有列表和tab两种
 * 列表再抽取一层处理RecyclerView分页加载逻辑，传入不同接口解析数据不同，展示页面，adapter都是一样，loading剥离到list加载中
 * tab主要是歌手分3个子页面对应歌手歌曲，专辑，信息3个请求
 * Created by tc :)
 */
public abstract class DetailPageBaseFragment extends BaseFragment implements StickyHeaderLayout.IGetTargetViewListener,
        StickyHeaderLayout.IHeaderHiddenListener {

    // 基本的id，请求用的，不能为空
    protected static final String KEY_LIST_ID = "key_list_id";
    // 基本的的名字
    protected static final String KEY_LIST_NAME = "key_list_name";
    // 基本的图片地址
    protected static final String KEY_LIST_PIC = "key_list_pic";

    private View mHeadView;
    private SimpleDraweeView mHeadPicView;
    private StickyHeaderLayout mStickyHeaderLayout;
    private TitleBar mTitleBar;
    private Interpolator mInterpolator;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_list_page;
    }

    @Override
    public void init(View view, LayoutInflater inflater, Bundle savedInstanceState) {
        mTitleBar = view.findViewById(R.id.title_bar);
        mHeadPicView = view.findViewById(R.id.head_pic);
        mStickyHeaderLayout = view.findViewById(R.id.sticky_header_layout);
        FrameLayout headContainer = view.findViewById(R.id.list_page_head);
        FrameLayout contentContainer = view.findViewById(R.id.list_page_content);
        onCreateContentView(inflater, contentContainer);
        mHeadView = onCreateHeadView(inflater, headContainer);
        mStickyHeaderLayout.setGetTargetViewListener(this);
        mStickyHeaderLayout.setHeaderHiddenListener(this);
        ImageLoaderWrapper.getInstance().load(mHeadPicView, getPicUrl());
    }

    @Override
    protected View onCreateTitleView(LayoutInflater inflater, FrameLayout titleContainer) {
        return null;
    }

    /**
     * 接收url统一处理顶部大图
     */
    protected abstract String getPicUrl();

    /**
     * 具体类型（榜单/歌单/专辑/歌手）
     */
    protected abstract int getType();

    /**
     * 内容区
     */
    protected abstract View onCreateContentView(LayoutInflater inflater, FrameLayout contentContainer);

    /**
     * 头部信息区
     *
     * @return
     */
    protected abstract View onCreateHeadView(LayoutInflater inflater, FrameLayout headContainer);

    @Override
    public View getTargetView() {
        return null;
    }

    @Override
    public int getDockedBarHeight() {
        return mTitleBar.getHeight();
    }

    @Override
    public void onIsHidden(boolean hidden) {

    }

    @Override
    public void onHeaderScroll(float percent, int scrollDirection) {
        if (mInterpolator == null) {
            mInterpolator = new DecelerateInterpolator();
        }
        float curInterpolation = mInterpolator.getInterpolation(percent);
        if (mHeadView != null) {
            mHeadView.setAlpha(1 - curInterpolation);
        }
    }
}
