package com.tgithubc.kumao.module.featured;

import android.util.Log;

import com.tgithubc.kumao.base.BasePresenter;
import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.BannerResult;
import com.tgithubc.kumao.bean.FeaturedData;
import com.tgithubc.kumao.data.task.GetBannerTask;
import com.tgithubc.kumao.data.task.GetBillboardTask;
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
    private List<FeaturedData> mFeedData = new ArrayList<>();

    @Override
    public void getFeaturedData() {
        // 开始多个task，组合成一个数据流返回，不是自己的接口没办法
        Observable<GetBannerTask.ResponseValue> bannerTask =
                new GetBannerTask()
                        .execute(new GetBannerTask.RequestValues(
                                "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.plaza.getFocusPic",
                                new RxMap<String, String>()
                                        .put("num", "10")
                                        .build()));
        Observable<GetBillboardTask.ResponseValue> billboardTask =
                new GetBillboardTask()
                        .execute(new GetBillboardTask.RequestValues(
                                "http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.billboard.billList",
                                new RxMap<String, String>()
                                        .put("type", "1")
                                        .put("offset", "0")
                                        .put("size", "5")
                                        .build()));
        Subscription subscription = Observable.merge(bannerTask, billboardTask)
                .toList()
                .doOnNext(responseValues -> {
                    // cache
                })
                .subscribe(new HttpSubscriber<List<Task.ResponseValue>>() {
                    @Override
                    protected void onError(String msg, Throwable e) {
                        getView().showError();
                    }

                    @Override
                    public void onNext(List<Task.ResponseValue> responseValues) {
                        super.onNext(responseValues);
                        Observable.from(responseValues).forEach(value -> {
                            if (value instanceof GetBannerTask.ResponseValue) {
                                FeaturedData<BannerResult> bannerData = new FeaturedData<>();
                                bannerData.setType(FeaturedData.TYPE_BANNER);
                                bannerData.setData(((GetBannerTask.ResponseValue) value).getResult());
                                mFeedData.add(bannerData);
                            } /*else if (value instanceof GetBillboardListTask.ResponseValue) {
                                List<Billboard> billboardList = ((GetBillboardListTask.ResponseValue) value).getResult();
                                Log.d(TAG, "responseValue :" + billboardList);
                                Observable.from(billboardList).forEach(billboard -> {
                                    FeaturedData<Billboard> billboardData = new FeaturedData<>();
                                    billboardData.setType(FeaturedData.TYPE_BILLBOARD);
                                    billboardData.setData(billboard);
                                    mFeedData.add(billboardData);
                                });
                            }*/
                        });
                        Log.d(TAG, "mFeedData :" + mFeedData);
                        getView().showFeatureView(mFeedData);
                    }
                });
        subscribe(subscription);
    }
}
