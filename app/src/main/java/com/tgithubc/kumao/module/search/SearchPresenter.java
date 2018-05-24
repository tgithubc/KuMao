package com.tgithubc.kumao.module.search;

import android.text.TextUtils;
import android.util.Log;

import com.tgithubc.kumao.KuMao;
import com.tgithubc.kumao.base.BasePresenter;
import com.tgithubc.kumao.bean.Artist;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.KeyWord;
import com.tgithubc.kumao.bean.SearchResult;
import com.tgithubc.kumao.constant.Constant;
import com.tgithubc.kumao.data.task.GetHotWordTask;
import com.tgithubc.kumao.data.task.GetSearchResultTask;
import com.tgithubc.kumao.db.DbCore;
import com.tgithubc.kumao.http.HttpSubscriber;
import com.tgithubc.kumao.util.RxMap;

import java.util.List;

import rx.Observable;
import rx.Subscription;

/**
 * Created by tc :)
 */
public class SearchPresenter extends BasePresenter<ISearchContract.V> implements ISearchContract.P {

    private String mCurrentKeyWord;
    private int mCurrentPage = 1;

    @Override
    public void getHotWord() {
        Observable<GetHotWordTask.ResponseValue> hotWordTask
                = new GetHotWordTask()
                .execute(new GetHotWordTask.RequestValues(Constant.Api.URL_HOTWORD, null));
        Subscription subscription = hotWordTask
                .subscribe(new HttpSubscriber<GetHotWordTask.ResponseValue>() {

                    @Override
                    protected void onError(String msg, Throwable e) {
                        getView().showHotWordErrorTip();
                    }

                    @Override
                    public void onNext(GetHotWordTask.ResponseValue responseValue) {
                        super.onNext(responseValue);
                        List<String> hotword = responseValue.getResult();
                        getView().showHotWord(hotword);
                    }
                });
        addSubscribe(subscription);
    }

    @Override
    public void search(String keyword) {
        resetKeyWord();
        if (TextUtils.isEmpty(keyword) || keyword.replaceAll(" ", "").length() <= 0) {
            return;
        }
        mCurrentKeyWord = keyword;
        KuMao.getExecutorService().execute(() -> {
                    KeyWord entity = new KeyWord();
                    entity.setKeyWord(keyword);
                    entity.setSearchTime(System.currentTimeMillis());
                    DbCore.getInstance().getDaoSession().getKeyWordDao().insert(entity);
                }
        );
        Subscription subscription = getSearchTask(keyword, 1)
                .subscribe(new HttpSubscriber<GetSearchResultTask.ResponseValue>() {
                    @Override
                    protected void onError(String msg, Throwable e) {
                        getView().showError();
                    }

                    @Override
                    public void onNext(GetSearchResultTask.ResponseValue responseValue) {
                        super.onNext(responseValue);
                        getView().showSearchResult(responseValue.getResult());
                    }
                });
        addSubscribe(subscription);
    }

    private void resetKeyWord() {
        mCurrentKeyWord = "";
        mCurrentPage = 1;
    }

    @Override
    public void searchLoadMore() {
        if (TextUtils.isEmpty(mCurrentKeyWord) || mCurrentKeyWord.replaceAll(" ", "").length() <= 0) {
            return;
        }
        Subscription subscription = getSearchTask(mCurrentKeyWord, ++mCurrentPage)
                .subscribe(new HttpSubscriber<GetSearchResultTask.ResponseValue>() {
                    @Override
                    protected void onError(String msg, Throwable e) {
                        getView().loadMoreError();
                    }

                    @Override
                    public void onNext(GetSearchResultTask.ResponseValue responseValue) {
                        super.onNext(responseValue);
                        List<BaseData> data = responseValue.getResult();
                        if (data.isEmpty()) {
                            getView().loadMoreFinish();
                        }
                        getView().loadMoreRefresh(data);
                    }
                });
        addSubscribe(subscription);
    }

    private Observable<GetSearchResultTask.ResponseValue> getSearchTask(String keyword, int page) {
        return new GetSearchResultTask()
                .execute(new GetSearchResultTask.RequestValues(Constant.Api.URL_SEARCH,
                        new RxMap<String, String>()
                                .put("page_size", "25")
                                .put("page_no", String.valueOf(page))
                                .put("query", keyword)
                                .build()));
    }

}
