package com.tgithubc.kumao.module.songlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.tgithubc.kumao.R;
import com.tgithubc.kumao.base.BaseFragment;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.Billboard;

/**
 * Created by tc :)
 */
public class SongListFragment extends BaseFragment implements ISongListContract.V {

    // 歌单或者榜单的id，请求用的，不能为空
    private static final String KEY_LIST_ID = "key_list_id";
    // 歌单或者榜单的名字
    private static final String KEY_LIST_NAME = "key_list_name";
    // 歌单或者榜单的图片
    private static final String KEY_LIST_PIC = "key_list_pic";
    // 榜单的描述
    private static final String KEY_BILLBOARD_DESC = "key_billboard_desc";
    // 榜单还是歌单 0歌单 1榜单
    private static final String KEY_SHOW_TYPE = "key_show_type";

    private static final int TYPE_BILLBOARD = 1;
    private static final int TYPE_SONGLIST = 0;

    private SongListPresenter mPresenter;
    private int mShowType;


    public static SongListFragment newInstance(BaseData data) {
        SongListFragment fragment = new SongListFragment();
        Object obj = data.getData();
        Bundle bundle = new Bundle();
        if (obj instanceof Billboard) {
            Billboard.Info info = ((Billboard) obj).getBillboardInfo();
            bundle.putInt(KEY_SHOW_TYPE, TYPE_BILLBOARD);
            bundle.putInt(KEY_LIST_ID, info.getBillboardType());
            bundle.putString(KEY_LIST_PIC, info.getPic_s260());
            bundle.putString(KEY_LIST_NAME, info.getName());
            bundle.putString(KEY_BILLBOARD_DESC, info.getComment());
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mShowType = bundle.getInt(KEY_SHOW_TYPE);
        }
        mPresenter = new SongListPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public boolean isShowLCEE() {
        return true;
    }

    @Override
    protected View onCreateTitleView(LayoutInflater inflater, FrameLayout titleContainer) {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_songlist;
    }

    @Override
    public void init(View view, LayoutInflater inflater, Bundle savedInstanceState) {

    }
}
