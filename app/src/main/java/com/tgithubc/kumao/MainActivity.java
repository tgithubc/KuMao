package com.tgithubc.kumao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tgithubc.kumao.widget.bottomtab.BottomTabItemView;
import com.tgithubc.kumao.widget.bottomtab.BottomTabLayout;


public class MainActivity extends AppCompatActivity {

    private BottomTabLayout mBottomTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomTabLayout = (BottomTabLayout) findViewById(R.id.bottom_tab_layout);
        mBottomTabLayout.createAdapter()
                .addTab(newTab("我的", R.mipmap.mine_normal, R.mipmap.mine_selected))
                .addTab(newTab("精选", R.mipmap.featured_normal, R.mipmap.featured_selected))
                .addTab(newTab("排行", R.mipmap.ranking_normal, R.mipmap.ranking_selected))
                .addTab(newTab("设置", R.mipmap.setting_normal, R.mipmap.setting_selected))
                .build();
        mBottomTabLayout.setSelected(1);
    }

    private BottomTabItemView newTab(String text, int normalDrawable, int checkedDrawable) {
        BottomTabItemView itemView = new BottomTabItemView(this);
        itemView.initialize(text, normalDrawable, checkedDrawable);
        return itemView;
    }
}
