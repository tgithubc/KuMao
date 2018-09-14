package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.SongListArray;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import rx.Observable;

/**
 *
 * Created by tc :)
 */
public class GetSongListArrayTask extends Task<Task.CommonRequestValue, GetSongListArrayTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(CommonRequestValue requestValues) {
        return RepositoryProvider.getRepository()
                .getSongListArrary(requestValues.getUrl(), requestValues.getParameter())
                .map(result -> {
                    // 有可能2列有可能3列
                    result.setType(requestValues.getUiType());
                    return new ResponseValue(result);
                });
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private SongListArray result;

        public ResponseValue(@NonNull SongListArray result) {
            this.result = result;
        }

        public SongListArray getResult() {
            return result;
        }
    }
}
