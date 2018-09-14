package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.Banner;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.constant.Constant;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import rx.Observable;

/**
 * Created by tc :)
 */
public class GetBannerTask extends Task<Task.CommonRequestValue, GetBannerTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(CommonRequestValue requestValues) {
        return RepositoryProvider.getRepository()
                .getBanner(requestValues.getUrl(), requestValues.getParameter())
                .map(banner -> {
                    // 应该就这一种展现形式的我就直接在这设置了
                    banner.setType(Constant.UIType.TYPE_BANNER);
                    return new ResponseValue(banner);
                });
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private Banner result;

        public ResponseValue(@NonNull Banner result) {
            this.result = result;
        }

        public Banner getResult() {
            return result;
        }
    }
}
