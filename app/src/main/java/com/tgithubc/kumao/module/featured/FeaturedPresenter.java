package com.tgithubc.kumao.module.featured;

import android.util.Log;

import com.tgithubc.kumao.base.BasePresenter;
import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.constant.Constant;
import com.tgithubc.kumao.data.task.GetBannerTask;
import com.tgithubc.kumao.data.task.GetBillboardListTask;
import com.tgithubc.kumao.http.HttpSubscriber;
import com.tgithubc.kumao.util.RxMap;


import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;

/**
 * 样式排序
 * banner
 * 热门歌单
 * 推荐歌
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
                .merge(runBannerTask(), runBillboardListTask())
                .toList()
                .subscribe(new FeaturedHttpSubscriber());
        addSubscribe(subscription);
    }

    private Observable<GetBannerTask.ResponseValue> runBannerTask() {
        return new GetBannerTask()
                .execute(new GetBannerTask.RequestValue(
                        Constant.Api.URL_BANNER,
                        new RxMap<String, String>()
                                .put("num", "10")
                                .build()));
    }

    private Observable<GetBillboardListTask.ResponseValue> runBillboardListTask() {
        // 其他类型
        return null;
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
        public void onNext(List<Task.ResponseValue> responseValues) {
            super.onNext(responseValues);
            Observable.from(responseValues).forEach(value -> {
                if (value instanceof GetBannerTask.ResponseValue) {
                    mFeedData.add(((GetBannerTask.ResponseValue) value).getResult());
                } else {
                    // 其他类型
                }
            });
            Log.d(TAG, "mFeedData :" + mFeedData);
            getView().showContent();
            getView().showFeatureView(mFeedData);
        }
    }
}
