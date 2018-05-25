package com.tgithubc.kumao.module.search;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tgithubc.kumao.R;
import com.tgithubc.kumao.base.BaseFragment;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.KeyWord;
import com.tgithubc.kumao.widget.TagLayout;

import java.util.List;

/**
 * Created by tc :)
 */
public class SearchFragment extends BaseFragment implements ISearchContract.V, TagLayout.OnTagClickListener,
        View.OnClickListener, TextView.OnEditorActionListener {

    private SearchPresenter mPresenter;
    private TagLayout mHotWordLayout;
    private TextView mHotWordErrorTip;
    private ScrollView mSearchTipLayout;
    private EditText mEditText;
    private RecyclerView mSearchResultRV;
    private RecyclerView mSearchHistoryRV;
    private SearchResultAdapter mAdapter;
    private SearchHistoryAdapter mHistoryAdapter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new SearchPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void init(View view, LayoutInflater inflater, Bundle savedInstanceState) {
        mSearchTipLayout = view.findViewById(R.id.search_tip_layout);
        mHotWordLayout = view.findViewById(R.id.search_hotword_layout);
        mHotWordErrorTip = view.findViewById(R.id.search_hotword_error_tip);
        mSearchHistoryRV = view.findViewById(R.id.search_history_recycler_view);
        mSearchResultRV = view.findViewById(R.id.search_result_recycler_view);
        mHotWordLayout.setOnTagClickListener(this);
        mEditText = view.findViewById(R.id.search_bar_et);
        mEditText.setOnEditorActionListener(this);
        view.findViewById(R.id.search_go).setOnClickListener(this);
        view.findViewById(R.id.search_back_icon).setOnClickListener(this);

        mSearchResultRV.setVisibility(View.GONE);
        mSearchResultRV.setHasFixedSize(true);
        mSearchResultRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SearchResultAdapter(getContext(), null);
        mAdapter.bindToRecyclerView(mSearchResultRV);
        mAdapter.setOnLoadMoreListener(() -> mPresenter.searchLoadMore(), mSearchResultRV);

        mSearchHistoryRV.setHasFixedSize(true);
        mSearchHistoryRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mHistoryAdapter = new SearchHistoryAdapter(null);
        mHistoryAdapter.bindToRecyclerView(mSearchHistoryRV);
        mPresenter.getHotWord();
        mPresenter.getSearchHistory();
    }

    @Override
    protected View onCreateTitleView(LayoutInflater inflater, FrameLayout titleContainer) {
        return inflater.inflate(R.layout.titlebar_search, titleContainer, false);
    }

    @Override
    public void showHotWord(List<String> hotword) {
        if (hotword != null && !hotword.isEmpty()) {
            mHotWordLayout.setTags(hotword);
        } else {
            mHotWordErrorTip.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showHotWordErrorTip() {
        mHotWordLayout.setVisibility(View.GONE);
        mHotWordErrorTip.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSearchResult(List<BaseData> result) {
        mSearchTipLayout.setVisibility(View.GONE);
        mSearchResultRV.setVisibility(View.VISIBLE);
        mAdapter.setNewData(result);
    }

    @Override
    public void loadMoreError() {
        mAdapter.loadMoreFail();
    }

    @Override
    public void loadMoreFinish() {
        mAdapter.loadMoreEnd(true);
    }

    @Override
    public void loadMoreRefresh(List<BaseData> data) {
        mAdapter.addData(data);
        mAdapter.loadMoreComplete();
    }

    @Override
    public void showSearchHistory(List<KeyWord> historyList) {
        mHistoryAdapter.setNewData(historyList);
    }

    @Override
    public void onTagClick(String tag) {
        mPresenter.search(tag);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_back_icon:
                closeFragment();
                break;
            case R.id.search_go:
                mPresenter.search(mEditText.getText().toString().trim());
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_SEARCH == actionId) {
            mPresenter.search(mEditText.getText().toString().trim());
        }
        return false;
    }
}
