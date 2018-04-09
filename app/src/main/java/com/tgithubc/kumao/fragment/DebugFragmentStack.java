package com.tgithubc.kumao.fragment;

import java.util.List;

/**
 * Created by tc :)
 */
public class DebugFragmentStack {

    public String mFragmentTag;
    public List<DebugFragmentStack> mChildFragmentStack;

    public DebugFragmentStack(String tag, List<DebugFragmentStack> childFragmentStack) {
        this.mFragmentTag = tag;
        this.mChildFragmentStack = childFragmentStack;
    }
}
