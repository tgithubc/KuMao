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

import rx.Subscription;

import static android.R.attr.type;

/**
 * Created by tc :)
 */
public class DetailListPagePresenter extends BasePresenter<IDetailListPageContract.V> implements IDetailListPageContract.P {

    private int mType;

    public DetailListPagePresenter(int type) {
        this.mType = type;
    }

    @Override
    public void getSongList(Map<String, String> requestValue) {
        switch (mType) {
            case DetailPageBaseFragment.TYPE_LIST_BILLBOARD:
                requestBillboard(requestValue);
                break;
            default:
                break;
        }
    }

    private void requestBillboard(Map<String, String> requestValue) {
        Subscription subscription = new GetBillboardTask()
                .execute(new GetBillboardTask.RequestValue(Constant.Api.URL_BILLBOARD, requestValue))
                .subscribe(new DetailListSubscriber());
        addSubscribe(subscription);
    }

    private class DetailListSubscriber extends HttpSubscriber<Task.ResponseValue> {

        @Override
        public void onStart() {
            super.onStart();
            getView().showLoading();
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
                case DetailPageBaseFragment.TYPE_LIST_BILLBOARD:
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
