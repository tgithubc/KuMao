package com.tgithubc.kumao.module.detailpage.base.list;

import com.tgithubc.kumao.base.BasePresenter;
import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.data.task.GetBillboardTask;
import com.tgithubc.kumao.http.HttpSubscriber;

import java.util.List;

import rx.Observable;
import rx.Subscription;

/**
 * Created by tc :)
 */
public class DetailListPagePresenter extends BasePresenter<IDetailListPageContract.V> implements IDetailListPageContract.P {

    @Override
    public void getSongList(int type, Task.RequestValue requestValue) {
        requestByType(type, (GetBillboardTask.RequestValue) requestValue);
    }

    private void requestByType(int type, GetBillboardTask.RequestValue requestValue) {
        switch (type) {
            case DetailListPageBaseFragment.KEY_LIST_BILLBOARD:
                requestBillboard(type, requestValue);
                break;
            default:
                break;
        }
    }

    private void requestBillboard(int type, GetBillboardTask.RequestValue requestValue) {
        Observable<GetBillboardTask.ResponseValue> observable = new GetBillboardTask().execute(requestValue);
        Subscription subscription = observable.subscribe(new DetailListSubscriber(type));
        addSubscribe(subscription);
    }

    private class DetailListSubscriber extends HttpSubscriber<Task.ResponseValue> {

        private int mType;

        public DetailListSubscriber(int type) {
            super();
            this.mType = type;
        }

        @Override
        protected void onError(String msg, Throwable e) {

        }

        @Override
        public void onNext(Task.ResponseValue responseValue) {
            super.onNext(responseValue);
            responseByType((GetBillboardTask.ResponseValue) responseValue);
        }

        private void responseByType(GetBillboardTask.ResponseValue responseValue) {
            List<Song> songList = null;
            switch (mType) {
                case DetailListPageBaseFragment.KEY_LIST_BILLBOARD:
                    songList = responseValue.getResult().getSongList();
                    break;
                default:
                    break;
            }
            if (songList != null) {
                getView().showSongList(songList);
            }
        }
    }
}
