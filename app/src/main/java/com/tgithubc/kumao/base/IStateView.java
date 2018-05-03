package com.tgithubc.kumao.base;

/**
 * Created by tc :)
 */

public interface IStateView extends IView {

    void showEmpty();

    void showError();

    void showLoading();

    void showContent();
}
