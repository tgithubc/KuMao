package com.tgithubc.kumao;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.tgithubc.kumao.base.BaseActivity;
import com.tgithubc.kumao.fragment.FragmentOperation;
import com.tgithubc.kumao.fragment.OnFragmentStackChangeListener;
import com.tgithubc.kumao.message.IObserver;
import com.tgithubc.kumao.message.MessageBus;
import com.tgithubc.kumao.module.HomePageAdapter;
import com.tgithubc.kumao.module.featured.FeaturedFragment;
import com.tgithubc.kumao.module.mine.MineFragment;
import com.tgithubc.kumao.module.mv.MvFragment;
import com.tgithubc.kumao.module.playpage.PlayFragment;
import com.tgithubc.kumao.module.ranking.RankingFragment;
import com.tgithubc.kumao.module.search.SearchFragment;
import com.tgithubc.kumao.observer.IKuMaoObserver.IFragmentSwipeBackObserver;
import com.tgithubc.kumao.widget.musicnote.MusicalNoteLayout;
import com.tgithubc.view.lib.BottomTabItemView;
import com.tgithubc.view.lib.BottomTabLayout;
import com.tgithubc.view.lib.OnTabSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OnFragmentStackChangeListener, View.OnClickListener {

    private HomePageAdapter mAdapter;
    private ViewPager mViewPager;
    private MusicalNoteLayout mFloatMusicVIew;

    private IObserver mFragmentSwipeBackObserver = (IFragmentSwipeBackObserver) this::setViewPagerVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.activity_main);
        FragmentOperation.getInstance().bind(R.id.fragment_container_id, this);
        initView();
        registerMessage();
        // 先简单来一下，后面适配权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterMessage();
        FragmentOperation.getInstance().unBind();
    }

    private void initView() {
        TextView searchView = (TextView) findViewById(R.id.top_search);
        searchView.setOnClickListener(this);
        mFloatMusicVIew = (MusicalNoteLayout) findViewById(R.id.music_note_layout);
        mFloatMusicVIew.setOnClickListener(this);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        BottomTabLayout tabLayout = (BottomTabLayout) findViewById(R.id.bottom_tab_layout);
        tabLayout.createAdapter()
                .addTab(newTab("我的", R.drawable.mine_normal, R.drawable.mine_selected))
                .addTab(newTab("精选", R.drawable.featured_normal, R.drawable.featured_selected))
                .addTab(newTab("榜单", R.drawable.ranking_normal, R.drawable.ranking_selected))
                .addTab(newTab("MV", R.drawable.setting_normal, R.drawable.setting_selected))
                .build();

        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(MineFragment.newInstance());
        fragments.add(FeaturedFragment.newInstance());
        fragments.add(RankingFragment.newInstance());
        fragments.add(MvFragment.newInstance());
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
        if (top instanceof PlayFragment) {
            mFloatMusicVIew.setVisibility(View.GONE);
            return;
        }
        mFloatMusicVIew.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPopFragment(Fragment top) {
        // full no note
        // sub note
        if (top instanceof PlayFragment) {
            mFloatMusicVIew.setVisibility(View.GONE);
            return;
        }
        mFloatMusicVIew.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShowMainLayer() {
        // no note
        // show viewpager
        mFloatMusicVIew.setVisibility(View.GONE);
        setViewPagerVisible(true);
    }

    @Override
    public void onHideMainLayer(boolean isHide) {
        // hide viewpager
        setViewPagerVisible(!isHide);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_search:
                FragmentOperation.getInstance().showFullFragment(SearchFragment.newInstance());
                break;
            case R.id.music_note_layout:
                FragmentOperation.getInstance().showFullFragment(PlayFragment.newInstance());
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (FragmentOperation.getInstance().onKeyDown(keyCode, event)) {
            return true;
        }
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (!FragmentOperation.getInstance().pop()) {
                    moveTaskToBack(true);
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
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
            FragmentOperation.getInstance().showFullFragment(PlayFragment.newInstance());
        }
    }
}
