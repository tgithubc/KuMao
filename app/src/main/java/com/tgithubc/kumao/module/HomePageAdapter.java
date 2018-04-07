package com.tgithubc.kumao.module;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by tc :)
 */
public class HomePageAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public HomePageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    public Fragment getFragment(int position) {
        if (mFragments != null) {
            return mFragments.get(position);
        } else {
            return null;
        }
    }
}
