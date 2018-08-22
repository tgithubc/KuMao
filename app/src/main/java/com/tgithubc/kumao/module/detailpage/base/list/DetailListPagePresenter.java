package com.tgithubc.kumao.module.detailpage.base.list;

import com.tgithubc.kumao.base.BasePresenter;
import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.constant.Constant;
import com.tgithubc.kumao.data.task.GetBillboardTask;
import com.tgithubc.kumao.http.HttpSubscriber;
import com.tgithubc.kumao.module.detailpage.base.DetailPageBaseFragment;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;

/**
 * Created by tc :)
 */
public class DetailListPagePresenter extends BasePresenter<IDetailListPageContract.V> implements IDetailListPageContract.P {

    // 一页去请求50条，这个分页很不准，返回的数据条数很大可能不是50，最不能忍的是has more的标记也是不准的
    private static final int PAGE_SIZE = 50;
    // 偏移位置，从哪开始再取50条
    private int mCurrentOffset;
    private int mType;

    public DetailListPagePresenter(int type) {
        this.mType = type;
    }

    @Override
    public void getSongList(Map<String, String> requestValue, int offset) {
        // +上分页相关的公共请求参数
        requestValue.put("size", String.valueOf(PAGE_SIZE));
        requestValue.put("offset", String.valueOf(offset));
        Observable observable = requestByType(requestValue);
        if (observable != null) {
            Subscription subscriber = observable.subscribe(new DetailListSubscriber());
            addSubscribe(subscriber);
        }
    }

    @Override
    public void loadMore(Map<String, String> requestValue) {
        // +上分页相关的公共请求参数
        requestValue.put("size", String.valueOf(PAGE_SIZE));
        requestValue.put("offset", String.valueOf(mCurrentOffset));
        Observable observable = requestByType(requestValue);
        if (observable != null) {
            Subscription subscriber = observable.subscribe(new LoadMoreSubscriber());
            addSubscribe(subscriber);
        }
    }

    private Observable requestByType(Map<String, String> requestValue) {
        switch (mType) {
            case DetailPageBaseFragment.TYPE_LIST_BILLBOARD:
                return requestBillboard(requestValue);
            default:
                break;
        }
        return null;
    }

    private List<Song> responseByType(Task.ResponseValue responseValue) {
        List<Song> songList = null;
        switch (mType) {
            case DetailPageBaseFragment.TYPE_LIST_BILLBOARD:
                songList = ((GetBillboardTask.ResponseValue) responseValue).getResult().getSongList();
                break;
            default:
                break;
        }
        return songList;
    }

    private Observable requestBillboard(Map<String, String> requestValue) {
        return new GetBillboardTask()
                .execute(new GetBillboardTask.RequestValue(Constant.Api.URL_BILLBOARD, requestValue));
    }

    private class LoadMoreSubscriber extends HttpSubscriber<Task.ResponseValue> {

        @Override
        protected void onError(String msg, Throwable e) {
            getView().loadMoreError();
        }

        @Override
        public void onNext(Task.ResponseValue responseValue) {
            List<Song> songList = responseByType(responseValue);
            if (songList != null && !songList.isEmpty()) {
                mCurrentOffset += songList.size();
                getView().loadMoreRefresh(songList);
            } else {
                getView().loadMoreFinish();
            }
        }
    }

    private class DetailListSubscriber extends HttpSubscriber<Task.ResponseValue> {

        @Override
        public void onStart() {
            getView().showLoading();
        }

        @Override
        protected void onError(String msg, Throwable e) {
            getView().showError();
        }

        @Override
        public void onNext(Task.ResponseValue responseValue) {
            List<Song> songList = responseByType(responseValue);
            if (songList != null && !songList.isEmpty()) {
                mCurrentOffset += songList.size();
                getView().showSongList(songList);
            } else {
                getView().showError();
            }
        }
    }
}
