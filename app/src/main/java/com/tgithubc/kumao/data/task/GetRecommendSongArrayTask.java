package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.RecommendSongArray;
import com.tgithubc.kumao.constant.Constant;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import rx.Observable;

/**
 * Created by tc :)
 */
public class GetRecommendSongArrayTask extends Task<Task.CommonRequestValue, GetRecommendSongArrayTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(CommonRequestValue requestValues) {
        return RepositoryProvider.getRepository()
                .getRecommendSongArray(requestValues.getUrl(), requestValues.getParameter())
                .map(result -> {
                    result.setType(Constant.UIType.TYPE_RECOMMEND_SONG);
                    return new ResponseValue(result);
                });
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private RecommendSongArray songArray;

        public ResponseValue(@NonNull RecommendSongArray billboard) {
            this.songArray = billboard;
        }

        public RecommendSongArray getResult() {
            return songArray;
        }
    }
}
