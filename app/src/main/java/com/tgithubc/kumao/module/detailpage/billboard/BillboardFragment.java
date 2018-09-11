package com.tgithubc.kumao.module.detailpage.billboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.module.detailpage.base.list.DetailListPageFragment;
import com.tgithubc.kumao.module.detailpage.base.list.DetailListPagePresenter;
import com.tgithubc.kumao.module.detailpage.base.list.IDetailListPageContract;
import com.tgithubc.kumao.util.RxMap;

/**
 * 榜单
 * Created by tc :)
 */
public class BillboardFragment extends DetailListPageFragment implements IDetailListPageContract.V {

    // 榜单的描述
    private static final String KEY_BILLBOARD_DESC = "key_billboard_desc";

    private TextView mBillboardDescView;
    private String mBillboardDesc;

    public static BillboardFragment newInstance(Billboard data) {
        BillboardFragment fragment = new BillboardFragment();
        Bundle bundle = new Bundle();
        Billboard.Info info = data.getBillboardInfo();
        bundle.putString(KEY_LIST_ID, String.valueOf(info.getBillboardType()));
        bundle.putString(KEY_LIST_PIC, info.getPic_s444());
        bundle.putString(KEY_LIST_NAME, info.getName());
        bundle.putString(KEY_BILLBOARD_DESC, info.getComment());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mBillboardDesc = bundle.getString(KEY_BILLBOARD_DESC);
        }
    }

    @Override
    protected DetailListPagePresenter createPresenter() {
        return new DetailListPagePresenter(TYPE_LIST_BILLBOARD, new RxMap<String, String>()
                .put("type", mId)
                .build());
    }

    @Override
    public View onCreateHeadView(LayoutInflater inflater, FrameLayout headContainer) {
        View view = inflater.inflate(R.layout.detail_page_header_billboard, headContainer, true);
        mBillboardDescView = view.findViewById(R.id.billboard_desc);
        mBillboardDescView.setText(mBillboardDesc);
        return view;
    }

    @Override
    public int getType() {
        return TYPE_LIST_BILLBOARD;
    }
}
