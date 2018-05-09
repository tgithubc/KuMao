package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import rx.Observable;

/**
 * Created by tc :)
 */
public class GetBillboardTask extends Task<GetBillboardTask.RequestValues, GetBillboardTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(RequestValues requestValues) {
        return RepositoryProvider.getTasksRepository()
                .getBillboard(requestValues.getType(), requestValues.getOffset(), requestValues.getSize())
                .map(result -> new ResponseValue(result));
    }

    public static final class RequestValues implements Task.RequestValues {

        private int type;
        private int offset;
        private int size;

        public RequestValues(int type, int offset, int size) {
            this.type = type;
            this.offset = offset;
            this.size = size;
        }

        public int getType() {
            return type;
        }

        public int getOffset() {
            return offset;
        }

        public int getSize() {
            return size;
        }
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private Billboard mResult;

        public ResponseValue(@NonNull Billboard result) {
            mResult = result;
        }

        public Billboard getResult() {
            return mResult;
        }
    }
}
