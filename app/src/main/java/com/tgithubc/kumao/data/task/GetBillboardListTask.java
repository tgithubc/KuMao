package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.constant.Constant;
import com.tgithubc.kumao.data.repository.RepositoryProvider;
import com.tgithubc.kumao.util.RxMap;

import java.util.List;

import rx.Observable;

/**
 * Created by tc :)
 */
public class GetBillboardListTask extends Task<Task.CommonRequestValue, GetBillboardListTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(CommonRequestValue requestValues) {
        return RepositoryProvider.getRepository()
                .getBillboardList(requestValues.getUrl(), requestValues.getParameter())
                .flatMap(this::requestNewBillboard)
                .flatMap(this::wrapperData);
    }

    // 得嵌套请求一次新歌榜的前三名的专辑封面图
    private Observable<? extends List<Billboard>> requestNewBillboard(final List<Billboard> billboards) {
        Billboard newBillboard = null;
        for (Billboard billboard : billboards) {
            if (billboard.getBillboardInfo().getBillboardType() == Constant.Api.BILLBOARD_TYPE_NEW) {
                newBillboard = billboard;
                break;
            }
        }
        if (newBillboard != null) {
            List<Song> songArrary = newBillboard.getSongList();
            if (songArrary != null && songArrary.size() >= 3) {
                Observable<Song> s1 = requestSongInfo(songArrary.get(0));
                Observable<Song> s2 = requestSongInfo(songArrary.get(1));
                Observable<Song> s3 = requestSongInfo(songArrary.get(2));
                Billboard finalNewBillboard = newBillboard;
                // 聚合三首歌的详细信息，压合转换
                return Observable.merge(s1, s2, s3)
                        .toList()
                        .concatMap(songList -> {
                            finalNewBillboard.getSongList().clear();
                            finalNewBillboard.setSongList(songList);
                            return Observable.just(billboards);
                        });
            }
        }
        return Observable.just(billboards);
    }

    // 类型区分，方便展示
    private Observable<? extends ResponseValue> wrapperData(List<Billboard> result) {
        Observable.from(result).forEach(billboard -> {
            if (billboard.getBillboardInfo().getBillboardType() == Constant.Api.BILLBOARD_TYPE_NEW) {
                billboard.setType(Constant.UIType.TYPE_RANK_NEW_BILLBOARD);
            } else {
                billboard.setType(Constant.UIType.TYPE_RANK_BILLBOARD);
            }
        });
        return Observable.just(new ResponseValue(result));
    }

    private Observable<Song> requestSongInfo(Song song) {
        return RepositoryProvider.getRepository()
                .getSongInfo(Constant.Api.URL_SONGINFO, new RxMap<String, String>().put("songid", song.getSongId()).build());
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private List<Billboard> result;

        public ResponseValue(@NonNull List<Billboard> result) {
            this.result = result;
        }

        public List<Billboard> getResult() {
            return result;
        }
    }
}
