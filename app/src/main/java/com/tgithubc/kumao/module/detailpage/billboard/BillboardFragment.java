package com.tgithubc.kumao.module.detailpage.billboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tgithubc.kumao.R;
import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.constant.Constant;
import com.tgithubc.kumao.data.task.GetBillboardTask;
import com.tgithubc.kumao.module.detailpage.base.list.DetailListPageBaseFragment;
import com.tgithubc.kumao.module.detailpage.base.list.IDetailListPageContract;
import com.tgithubc.kumao.util.RxMap;

/**
 * 榜单
 * Created by tc :)
 */
public class BillboardFragment extends DetailListPageBaseFragment implements IDetailListPageContract.V {

    // 榜单的描述
    private static final String KEY_BILLBOARD_DESC = "key_billboard_desc";

    private TextView mDescView;

    private String mPicUrl;
    private String mDesc;
    private int mId;

    public static BillboardFragment newInstance(BaseData data) {
        BillboardFragment fragment = new BillboardFragment();
        Object obj = data.getData();
        Bundle bundle = new Bundle();
        if (obj instanceof Billboard) {
            Billboard.Info info = ((Billboard) obj).getBillboardInfo();
            bundle.putInt(KEY_LIST_ID, info.getBillboardType());
            bundle.putString(KEY_LIST_PIC, info.getPic_s444());
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
            mId = bundle.getInt(KEY_LIST_ID);
            mDesc = bundle.getString(KEY_BILLBOARD_DESC);
            mPicUrl = bundle.getString(KEY_LIST_PIC);
        }
    }

    @Override
    protected String getPicUrl() {
        return mPicUrl;
    }

    @Override
    public View onCreateHeadView(LayoutInflater inflater, FrameLayout headContainer) {
        View view = inflater.inflate(R.layout.detail_page_header_billboard, headContainer, true);
        mDescView = view.findViewById(R.id.billboard_desc);
        mDescView.setText(mDesc);
        return view;
    }

    @Override
    public int getType() {
        return KEY_LIST_BILLBOARD;
    }

    @Override
    public Task.RequestValue getRequestValue() {
        return new GetBillboardTask.RequestValue(Constant.Api.URL_BILLBOARD, new RxMap<String, String>()
                .put("type", String.valueOf(mId))
                .build());
    }
}
