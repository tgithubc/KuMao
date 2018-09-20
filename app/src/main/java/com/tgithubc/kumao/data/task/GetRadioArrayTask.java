package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.RadioArray;
import com.tgithubc.kumao.constant.Constant;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import io.reactivex.Observable;

/**
 *
 * Created by tc :)
 */
public class GetRadioArrayTask extends Task<Task.CommonRequestValue, GetRadioArrayTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(CommonRequestValue requestValues) {
        return RepositoryProvider.getRepository()
                .getRadioArray(requestValues.getUrl(), requestValues.getParameter())
                .map(result -> {
                    result.setType(Constant.UIType.TYPE_RADIO_3S);
                    return new ResponseValue(result);
                });
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private RadioArray result;

        public ResponseValue(@NonNull RadioArray result) {
            this.result = result;
        }

        public RadioArray getResult() {
            return result;
        }
    }
}
