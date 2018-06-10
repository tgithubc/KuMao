package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import java.util.Map;

import rx.Observable;

/**
 * Created by tc :)
 */
public class GetSongInfoTask extends Task<GetSongInfoTask.RequestValue, GetSongInfoTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(RequestValue requestValues) {
        return RepositoryProvider.getRepository()
                .getSongInfo(requestValues.getUrl(), requestValues.getParameter())
                .map(ResponseValue::new);
    }

    public static final class RequestValue extends Task.CommonRequestValue {

        public RequestValue(String url, Map<String, String> parameter) {
            super(url, parameter);
        }
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private Song mResult;

        public ResponseValue(@NonNull Song result) {
            mResult = result;
        }

        public Song getResult() {
            return mResult;
        }
    }
}
