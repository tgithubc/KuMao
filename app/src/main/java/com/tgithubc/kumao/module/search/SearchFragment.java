package com.tgithubc.kumao.module.search;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
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

    private TagLayout mHotWordLayout;
    private TextView mHotWordErrorTip;
    private ImageView mKeyWordClearBtn;
    private ImageView mHistoryClearBtn;
    private ScrollView mSearchTipLayout;
    private EditText mEditText;
    private RecyclerView mSearchResultRV;
    private RecyclerView mSearchHistoryRV;

    private SearchPresenter mPresenter;
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
        mKeyWordClearBtn = view.findViewById(R.id.search_keyword_clear_btn);
        mHistoryClearBtn = view.findViewById(R.id.search_history_clear_btn);
        mHotWordLayout = view.findViewById(R.id.search_hotword_layout);
        mHotWordErrorTip = view.findViewById(R.id.search_hotword_error_tip);
        mSearchHistoryRV = view.findViewById(R.id.search_history_recycler_view);
        mSearchResultRV = view.findViewById(R.id.search_result_recycler_view);
        mHotWordLayout.setOnTagClickListener(this);
        mEditText = view.findViewById(R.id.search_bar_et);
        mEditText.setOnEditorActionListener(this);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString();
                if (TextUtils.isEmpty(query)) {
                    mKeyWordClearBtn.setVisibility(View.GONE);
                } else {
                    mKeyWordClearBtn.setVisibility(View.VISIBLE);
                }
            }
        });
        mKeyWordClearBtn.setOnClickListener(this);
        mHistoryClearBtn.setOnClickListener(this);
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
        mHistoryAdapter.setOnItemChildClickListener(getAdapterItemClickListener());
        mHistoryAdapter.bindToRecyclerView(mSearchHistoryRV);
        mHistoryAdapter.setEmptyView(R.layout.rv_item_search_history_empty);
        mPresenter.getHotWord();
        mPresenter.getSearchHistory();
    }

    @Override
    public boolean isShowLCEE() {
        return true;
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
    public void showSearchHistory(List<KeyWord> historyList) {
        if (historyList.isEmpty()) {
            return;
        }
        mHistoryAdapter.setNewData(historyList);
        mHistoryClearBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearSearchHistory() {
        mHistoryAdapter.setNewData(null);
        mHistoryClearBtn.setVisibility(View.GONE);
    }

    @Override
    public void refreshSearchHistory(int position) {
        mHistoryAdapter.remove(position);
        if (mHistoryAdapter.getData().isEmpty()) {
            mHistoryClearBtn.setVisibility(View.GONE);
        }
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
    public void onTagClick(String tag) {
        searchGo(tag);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_back_icon:
                closeFragment();
                break;
            case R.id.search_go:
                searchGo(mEditText.getText().toString());
                break;
            case R.id.search_keyword_clear_btn:
                mEditText.setText("");
                mSearchResultRV.setVisibility(View.GONE);
                mSearchTipLayout.setVisibility(View.VISIBLE);
                mPresenter.getSearchHistory();
                break;
            case R.id.search_history_clear_btn:
                mPresenter.clearSearchHistory();
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_SEARCH == actionId) {
            searchGo(mEditText.getText().toString());
        }
        return false;
    }

    private void searchGo(String keyWord) {
        String key = keyWord.replaceAll(" ", "");
        if (TextUtils.isEmpty(key)) {
            Toast.makeText(getContext(), "请输入正确的搜索词", Toast.LENGTH_LONG).show();
            return;
        }
        mEditText.setText(key);
        mPresenter.search(key);
    }

    private BaseQuickAdapter.OnItemChildClickListener getAdapterItemClickListener() {
        return (adapter, view, position) -> {
            List<KeyWord> data = adapter.getData();
            if (data.isEmpty() || position < 0 || position >= data.size()) {
                return;
            }
            switch (view.getId()) {
                case R.id.search_history_item:
                    String key = data.get(position).getKeyWord();
                    searchGo(key);
                    break;
                case R.id.search_history_del_btn:
                    KeyWord keyword = data.get(position);
                    mPresenter.deleteSearchHistory(keyword, position);
                    break;
            }
        };
    }
}
