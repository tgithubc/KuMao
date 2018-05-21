package com.tgithubc.kumao.module.search;

import android.text.TextUtils;

import com.tgithubc.kumao.base.BasePresenter;
import com.tgithubc.kumao.bean.Artist;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.SearchResult;
import com.tgithubc.kumao.constant.Constant;
import com.tgithubc.kumao.data.task.GetHotWordTask;
import com.tgithubc.kumao.data.task.GetSearchResultTask;
import com.tgithubc.kumao.http.HttpSubscriber;
import com.tgithubc.kumao.util.RxMap;

import java.util.List;

import rx.Observable;
import rx.Subscription;

/**
 * Created by tc :)
 */
public class SearchPresenter extends BasePresenter<ISearchContract.V> implements ISearchContract.P {

    private List<BaseData> mSearchResult;

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
        if (TextUtils.isEmpty(keyword) || keyword.replaceAll(" ", "").length() <= 0) {
            return;
        }
        Subscription subscription = getSearchTask(keyword, 1)
                .subscribe(new HttpSubscriber<GetSearchResultTask.ResponseValue>() {
                    @Override
                    protected void onError(String msg, Throwable e) {
                        getView().showError();
                    }

                    @Override
                    public void onNext(GetSearchResultTask.ResponseValue responseValue) {
                        super.onNext(responseValue);
                        mSearchResult = responseValue.getResult();
                        getView().showSearchResult(responseValue.getResult());
                    }
                });
        addSubscribe(subscription);
    }

    @Override
    public void searchLoadMore(String keyword, int page) {
        Subscription subscription = getSearchTask(keyword, page)
                .subscribe(new HttpSubscriber<GetSearchResultTask.ResponseValue>() {
                    @Override
                    protected void onError(String msg, Throwable e) {
                        // load more error
                    }

                    @Override
                    public void onNext(GetSearchResultTask.ResponseValue responseValue) {
                        super.onNext(responseValue);
                        // add new data
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
