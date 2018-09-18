package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by tc :)
 */
public class GetHotWordTask extends Task<Task.CommonRequestValue, GetHotWordTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(CommonRequestValue requestValues) {
        return RepositoryProvider.getRepository()
                .getHotWord(requestValues.getUrl())
                .map(ResponseValue::new);
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private List<String> hotWord;

        public ResponseValue(@NonNull List<String> hotWord) {
            this.hotWord = hotWord;
        }

        public List<String> getResult() {
            return hotWord;
        }
    }
}
