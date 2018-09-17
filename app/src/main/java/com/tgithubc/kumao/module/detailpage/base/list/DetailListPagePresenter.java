package com.tgithubc.kumao.module.detailpage.base.list;

import com.tgithubc.kumao.base.BasePresenter;
import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.bean.SongList;
import com.tgithubc.kumao.constant.Constant;
import com.tgithubc.kumao.data.task.GetBillboardTask;
import com.tgithubc.kumao.data.task.GetSongListTask;
import com.tgithubc.kumao.http.HttpSubscriber;
import com.tgithubc.kumao.module.detailpage.base.DetailPageBaseFragment;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by tc :)
 */
public class DetailListPagePresenter extends BasePresenter<IDetailListPageContract.V> implements IDetailListPageContract.P {

    // 一页去请求50条，这个分页很不准，返回的数据条数很大可能不是50
    // 最不能忍的是has more的标记也是不准的，更可气的是有些接口连这个不准的标记都没有
    private static final int PAGE_SIZE = 50;
    // 偏移位置，从哪开始再取50条
    private int mCurrentOffset;
    private int mType;
    private Map<String, String> mRequestValue;
    // 没法判断出来has more是否完结，只能对比返回的结果
    private List<Song> mSongList;

    public DetailListPagePresenter(int type, Map<String, String> requestValue) {
        this.mType = type;
        this.mRequestValue = requestValue;
    }

    @Override
    public void getSongList() {
        loadData(new DetailListSubscriber());
    }

    @Override
    public void loadMore() {
        loadData(new LoadMoreSubscriber());
    }

    @SuppressWarnings("unchecked")
    private void loadData(Subscriber subscriber) {
        Observable observable = requestByType();
        if (observable != null) {
            addSubscribe(observable.subscribe(subscriber));
        }
    }

    private Observable requestByType() {
        switch (mType) {
            case DetailPageBaseFragment.TYPE_LIST_BILLBOARD:
                // +上分页相关的公共请求参数
                mRequestValue.put("size", String.valueOf(PAGE_SIZE));
                mRequestValue.put("offset", String.valueOf(mCurrentOffset));
                return requestBillboard(mRequestValue);
            case DetailPageBaseFragment.TYPE_LIST_SONGLIST:
                return requestSongList(mRequestValue);
            default:
                break;
        }
        return null;
    }

    private List<Song> responseByType(Task.ResponseValue responseValue) {
        List<Song> songList = null;
        Object result = responseValue.getResult();
        switch (mType) {
            case DetailPageBaseFragment.TYPE_LIST_BILLBOARD:
                songList = ((Billboard) result).getSongList();
                break;
            case DetailPageBaseFragment.TYPE_LIST_SONGLIST:
                songList = ((SongList) result).getSongList();
                break;
            default:
                break;
        }
        return songList;
    }

    private Observable requestBillboard(Map<String, String> requestValue) {
        return new GetBillboardTask().execute(new Task.CommonRequestValue(Constant.Api.URL_BILLBOARD, requestValue));
    }

    private Observable requestSongList(Map<String, String> requestValue) {
        return new GetSongListTask().execute(new Task.CommonRequestValue(Constant.Api.URL_SONG_LIST_INFO, requestValue));
    }

    private class LoadMoreSubscriber extends HttpSubscriber<Task.ResponseValue> {

        @Override
        protected void onError(String msg, Throwable e) {
            getView().loadMoreError();
        }

        @Override
        public void onNext(Task.ResponseValue responseValue) {
            List<Song> songList = responseByType(responseValue);
            if ((mSongList != null && songList != null && mSongList.containsAll(songList))
                    || (songList == null || songList.isEmpty())) {
                getView().loadMoreFinish();
            } else {
                mCurrentOffset += songList.size();
                getView().loadMoreRefresh(songList);
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
                mSongList = songList;
                getView().showSongList(songList);
            } else {
                getView().showError();
            }
        }
    }
}
