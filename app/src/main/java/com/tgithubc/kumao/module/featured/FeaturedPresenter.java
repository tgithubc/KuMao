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
 * Created by tc :)
 */
public class FeaturedPresenter extends BasePresenter<IFeaturedContract.V> implements IFeaturedContract.P {

    private static final String TAG = "FeaturedPresenter";
    private List<BaseData> mFeedData = new ArrayList<>();

    @Override
    public void getFeaturedData() {
        // 开始多个task，组合成一个数据流返回，不是自己的接口没办法
        Subscription subscription =
                Observable
                        .merge(runBannerTask(), runBillboardListTask())
                        .toList()
                        .subscribe(new HttpSubscriber<List<Task.ResponseValue>>() {

                            @Override
                            public void onStart() {
                                super.onStart();
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
                                    } else if (value instanceof GetBillboardListTask.ResponseValue) {
                                        mFeedData.addAll(((GetBillboardListTask.ResponseValue) value).getResult());
                                    }
                                });
                                Log.d(TAG, "mFeedData :" + mFeedData);
                                getView().showContent();
                                getView().showFeatureView(mFeedData);
                            }
                        });
        addSubscribe(subscription);
    }

    private Observable<GetBannerTask.ResponseValue> runBannerTask() {
        return new GetBannerTask()
                .execute(new GetBannerTask.RequestValues(
                        Constant.Api.URL_BANNER,
                        new RxMap<String, String>()
                                .put("num", "10")
                                .build()));
    }

    private Observable<GetBillboardListTask.ResponseValue> runBillboardListTask() {
        List<Task.CommonRequestValues> parameters = new ArrayList<>();
        parameters.add(buildBillboardRequest(Constant.Api.BILLBOARD_TYPE_NEW));
        parameters.add(buildBillboardRequest(Constant.Api.BILLBOARD_TYPE_HOT));
        return new GetBillboardListTask().execute(new GetBillboardListTask.RequestValues(parameters));
    }

    private Task.CommonRequestValues buildBillboardRequest(int type) {
        return new Task.CommonRequestValues(
                Constant.Api.URL_BILLBOARD,
                new RxMap<String, String>()
                        .put("type", String.valueOf(type))
                        .put("offset", "0")
                        .put("size", "5")
                        .build());
    }
}
