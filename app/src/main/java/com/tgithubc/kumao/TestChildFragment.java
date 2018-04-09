package com.tgithubc.kumao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tgithubc.kumao.base.BaseFragment;

/**
 * Created by tc :)
 */
public class TestChildFragment extends BaseFragment {

    private static final String KEY_CONTENT = "KEY_CONTENT";
    private String mContent;

    public static TestChildFragment newInstance(String content) {
        TestChildFragment fragment = new TestChildFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_CONTENT, content);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mContent = bundle.getString(KEY_CONTENT);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_test_child;
    }

    @Override
    public void init(View view, LayoutInflater inflater, Bundle savedInstanceState) {
        ((TextView) view.findViewById(R.id.text)).setText(mContent);
    }
}
