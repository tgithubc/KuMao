package com.tgithubc.kumao;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tgithubc.kumao.fragment.FragmentOperation;
import com.tgithubc.kumao.fragment.OnFragmentStackChangeListener;
import com.tgithubc.kumao.module.HomePageAdapter;
import com.tgithubc.kumao.widget.bottomtab.BottomTabItemView;
import com.tgithubc.kumao.widget.bottomtab.BottomTabLayout;
import com.tgithubc.kumao.widget.bottomtab.OnTabSelectedListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnFragmentStackChangeListener {

    private HomePageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentOperation.getInstance().bind(R.id.fragment_container_id, this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentOperation.getInstance().unBind();
    }

    private void initView() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
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
        fragments.add(TestFragment.newInstance("精选"));
        fragments.add(TestFragment.newInstance("榜单"));
        fragments.add(TestFragment.newInstance("设置"));
        mAdapter = new HomePageAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(4);
        tabLayout.bindViewPage(viewPager);
        tabLayout.setSelected(1);
        tabLayout.addOnTabSelectedListener(new BottomTabClickListener());
    }

    private BottomTabItemView newTab(String text, int normalDrawable, int checkedDrawable) {
        BottomTabItemView itemView = new BottomTabItemView(this);
        itemView.initialize(text, normalDrawable, checkedDrawable);
        return itemView;
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
    }

    @Override
    public void onHideMainLayer(boolean isHide) {
        // hide viewpager
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
