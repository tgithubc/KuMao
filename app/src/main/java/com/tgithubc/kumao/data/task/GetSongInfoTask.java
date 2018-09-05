package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import rx.Observable;

/**
 * Created by tc :)
 */
public class GetSongInfoTask extends Task<Task.CommonRequestValue, GetSongInfoTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(CommonRequestValue requestValues) {
        return RepositoryProvider.getRepository()
                .getSongInfo(requestValues.getUrl(), requestValues.getParameter())
                .map(ResponseValue::new);
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private Song result;

        public ResponseValue(@NonNull Song result) {
            this.result = result;
        }

        public Song getResult() {
            return result;
        }
    }
}
