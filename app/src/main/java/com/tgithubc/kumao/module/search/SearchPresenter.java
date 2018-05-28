package com.tgithubc.kumao.module.search;

import android.text.TextUtils;

import com.tgithubc.kumao.base.BasePresenter;
import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.KeyWord;
import com.tgithubc.kumao.constant.Constant;
import com.tgithubc.kumao.data.task.DeleteAllSearchHistoryTask;
import com.tgithubc.kumao.data.task.DeleteSearchHistoryTask;
import com.tgithubc.kumao.data.task.GetHotWordTask;
import com.tgithubc.kumao.data.task.GetSearchHistoryTask;
import com.tgithubc.kumao.data.task.GetSearchResultTask;
import com.tgithubc.kumao.data.task.SaveSearchHistoryTask;
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
                .execute(new GetHotWordTask.RequestValue(Constant.Api.URL_HOTWORD, null));
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
        if (keyword.equals(mCurrentKeyWord)) {
            return;
        }
        // 搜索前重置关键词
        resetKeyWord();
        mCurrentKeyWord = keyword;
        getView().showLoading();
        // 存储合法的搜索词到数据库
        Subscription saveSubscription =
                new SaveSearchHistoryTask()
                        .execute(new SaveSearchHistoryTask.RequestValue(keyword))
                        .subscribe(responseValue -> {

                        });
        addSubscribe(saveSubscription);

        // 开个task去请求搜索结果
        Subscription querySubscription =
                getSearchTask(keyword, 1)
                        .subscribe(new HttpSubscriber<GetSearchResultTask.ResponseValue>() {
                            @Override
                            protected void onError(String msg, Throwable e) {
                                getView().showError();
                            }

                            @Override
                            public void onNext(GetSearchResultTask.ResponseValue responseValue) {
                                super.onNext(responseValue);
                                getView().showContent();
                                getView().showSearchResult(responseValue.getResult());
                            }
                        });
        addSubscribe(querySubscription);
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
        Subscription subscription =
                getSearchTask(mCurrentKeyWord, ++mCurrentPage)
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

    @Override
    public void getSearchHistory() {
        Subscription subscription =
                new GetSearchHistoryTask()
                        .execute(new Task.EmptyRequestValue())
                        .subscribe(responseValue -> getView().showSearchHistory(responseValue.getResult()));
        addSubscribe(subscription);
    }

    @Override
    public void clearSearchHistory() {
        Subscription subscription =
                new DeleteAllSearchHistoryTask()
                        .execute(new Task.EmptyRequestValue())
                        .subscribe(responseValue -> getView().clearSearchHistory());
        addSubscribe(subscription);
    }

    @Override
    public void deleteSearchHistory(KeyWord keyword, int position) {
        Subscription subscription =
                new DeleteSearchHistoryTask()
                        .execute(new DeleteSearchHistoryTask.RequestValue(keyword))
                        .subscribe(responseValue -> getView().refreshSearchHistory(position));
        addSubscribe(subscription);
    }

    private Observable<GetSearchResultTask.ResponseValue> getSearchTask(String keyword, int page) {
        return new GetSearchResultTask()
                .execute(new GetSearchResultTask.RequestValue(Constant.Api.URL_SEARCH,
                        new RxMap<String, String>()
                                .put("page_size", "25")
                                .put("page_no", String.valueOf(page))
                                .put("query", keyword)
                                .build()));
    }
}
