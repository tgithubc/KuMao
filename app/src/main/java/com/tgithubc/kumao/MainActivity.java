package com.tgithubc.kumao;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.tgithubc.kumao.base.BaseActivity;
import com.tgithubc.kumao.fragment.FragmentOperation;
import com.tgithubc.kumao.fragment.OnFragmentStackChangeListener;
import com.tgithubc.kumao.message.IObserver;
import com.tgithubc.kumao.message.MessageBus;
import com.tgithubc.kumao.module.HomePageAdapter;
import com.tgithubc.kumao.module.featured.FeaturedFragment;
import com.tgithubc.kumao.observer.IKuMaoObserver.IFragmentSwipeBackObserver;
import com.tgithubc.view.lib.BottomTabItemView;
import com.tgithubc.view.lib.BottomTabLayout;
import com.tgithubc.view.lib.OnTabSelectedListener;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OnFragmentStackChangeListener {

    private HomePageAdapter mAdapter;
    private ViewPager mViewPager;

    private IObserver mFragmentSwipeBackObserver = (IFragmentSwipeBackObserver) this::setViewPagerVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentOperation.getInstance().bind(R.id.fragment_container_id, this);
        initView();
        registerMessage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterMessage();
        FragmentOperation.getInstance().unBind();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        BottomTabLayout tabLayout = (BottomTabLayout) findViewById(R.id.bottom_tab_layout);
        tabLayout.createAdapter()
                .addTab(newTab("我的", R.mipmap.mine_normal, R.mipmap.mine_selected))
                .addTab(newTab("精选", R.mipmap.featured_normal, R.mipmap.featured_selected))
                .addTab(newTab("榜单", R.mipmap.ranking_normal, R.mipmap.ranking_selected))
                .addTab(newTab("设置", R.mipmap.setting_normal, R.mipmap.setting_selected))
                .build();
        tabLayout.setMessage(1, 999);

        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(TestFragment.newInstance("我的"));
        fragments.add(FeaturedFragment.newInstance());
        fragments.add(TestFragment.newInstance("榜单"));
        fragments.add(TestFragment.newInstance("设置"));
        mAdapter = new HomePageAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(4);
        tabLayout.bindViewPage(mViewPager);
        tabLayout.setSelected(1);
        tabLayout.addOnTabSelectedListener(new BottomTabClickListener());
    }

    private BottomTabItemView newTab(String text, int normalDrawable, int checkedDrawable) {
        BottomTabItemView itemView = new BottomTabItemView(this);
        itemView.initialize(text, normalDrawable, checkedDrawable);
        return itemView;
    }

    private void registerMessage() {
        MessageBus.instance().register(mFragmentSwipeBackObserver);
    }

    private void unRegisterMessage() {
        MessageBus.instance().unRegister(mFragmentSwipeBackObserver);
    }

    private void setViewPagerVisible(boolean isVisible) {
        mViewPager.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onPushFragment(Fragment top) {
        // full no note
        // sub note
    }

    @Override
    public void onPopFragment(Fragment top) {
        // full no note
        // sub note
    }

    @Override
    public void onShowMainLayer() {
        // no note
        // show viewpager
        setViewPagerVisible(true);
    }

    @Override
    public void onHideMainLayer(boolean isHide) {
        // hide viewpager
        setViewPagerVisible(!isHide);
    }

    private class BottomTabClickListener implements OnTabSelectedListener {

        @Override
        public void onTabSelected(int position, int prePosition) {

        }

        @Override
        public void onTabReselected(int position) {

        }

        @Override
        public void onCenterViewClicked(View centerView) {

        }
    }
}
