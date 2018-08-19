package com.tgithubc.kumao.module.listpage.billboard;


import com.tgithubc.kumao.base.IStateView;
import com.tgithubc.kumao.bean.Billboard;

/**
 * Created by tc :)
 */
public interface IBillboardContract {

    interface V extends IStateView {

        void showBillboard(Billboard billboard);
    }

    interface P {

        void getBillboard(int id);
    }
}
