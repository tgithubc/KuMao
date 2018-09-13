package com.tgithubc.kumao.module.featured;

import android.util.Log;

import com.tgithubc.kumao.base.BasePresenter;
import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.Title;
import com.tgithubc.kumao.constant.Constant;
import com.tgithubc.kumao.data.task.GetBannerTask;
import com.tgithubc.kumao.data.task.GetSongListArrayTask;
import com.tgithubc.kumao.http.HttpSubscriber;
import com.tgithubc.kumao.util.RxMap;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;


/**
 * 样式排序
 * banner
 * 推荐歌单（全部歌单）
 * 推荐单曲？？
 * 电台
 * 新专辑
 * 新歌
 * Created by tc :)
 */
public class FeaturedPresenter extends BasePresenter<IFeaturedContract.V> implements IFeaturedContract.P {

    private static final String TAG = "FeaturedPresenter";
    private List<BaseData> mFeedData = new ArrayList<>();

    @Override
    public void getFeaturedData() {
        // 开始多个task，组合成一个数据流返回，不是自己的接口没办法
        Subscription subscription = Observable
                .concat(executeBannerTask(), executeHotSongListTask())
                .toList()
                .subscribe(new FeaturedHttpSubscriber());
        addSubscribe(subscription);
    }

    /**
     * banner
     */
    private Observable<GetBannerTask.ResponseValue> executeBannerTask() {
        Task.CommonRequestValue rq = new Task.CommonRequestValue(
                Constant.Api.URL_BANNER,
                new RxMap<String, String>().put("num", "10").build());
        return new GetBannerTask().execute(rq);
    }

    /**
     * hot song list
     */
    private Observable<GetSongListArrayTask.ResponseValue> executeHotSongListTask() {
        Task.CommonRequestValue rq = new Task.CommonRequestValue(
                Constant.Api.URL_SONG_LIST_ARRARY,
                new RxMap<String, String>()
                        .put("page_no", "1")
                        .put("page_size", "6")
                        .build());
        return new GetSongListArrayTask().execute(rq);
    }

    private class FeaturedHttpSubscriber extends HttpSubscriber<List<Task.ResponseValue>> {

        @Override
        public void onStart() {
            getView().showLoading();
        }

        @Override
        protected void onError(String msg, Throwable e) {
            getView().showError();
        }

        @Override
        public void onNext(List<Task.ResponseValue> response) {
            for (Task.ResponseValue value : response) {
                if (value.getResult() == null) {
                    continue;
                }
                // 组装title数据
                if (value instanceof GetSongListArrayTask.ResponseValue) {
                    Title title = new Title();
                    title.setTitle("热门歌单");
                    title.setType(BaseData.TYPE_TITLE_MORE);
                    mFeedData.add(title);
                }
                mFeedData.add((BaseData) value.getResult());
            }
            Log.d(TAG, "mFeedData :" + mFeedData);
            getView().showContent();
            getView().showFeatureView(mFeedData);
        }
    }
}
