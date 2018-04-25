package com.tgithubc.kumao.http;


import android.util.Log;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Locale;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * 统一处理error
 * 其实还有些不是服务端错误的code，比如登陆失败的业务错误也应该提前预处理了丢个异常转到onError里来，不应该再走到onNext
 * Created by tc :)
 */
public abstract class ApiSubscriber<T> extends Subscriber<T> {

    private static final String TAG = "ApiException";

    // http error code
    private static final int ERROR_CODE_BAD_REQUEST = 400;
    private static final int ERROR_CODE_FORBIDDEN = 403;
    private static final int ERROR_CODE_NOT_FOUND = 404;
    private static final int ERROR_CODE_TIMEOUT = 408;
    private static final int ERROR_CODE_SERVER_ERROR = 500;

    private static final String MSG_NETWORK_ERROR = "网络错误";
    private static final String MSG_TIME_OUT = "网络请求超时";
    private static final String MSG_SERVER_ERROR = "服务器错误";
    private static final String MSG_NETWORK_CONNECTION_ERROR = "网络连接不可用，请检查或稍后重试";
    private static final String MSG_UNKNOWN_ERROR = "未知错误";

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onCompleted() {
        onFinally();
    }

    @Override
    public void onError(Throwable e) {
        resolveException(e);
        onFinally();
    }

    public void onFinally() {

    }

    private void resolveException(Throwable e) {
        Log.d(TAG, e.getMessage());
        if (e instanceof ApiException) {
            String msg = ((ApiException) e).getMsg();
            if (msg == null || msg.isEmpty()) {
                msg = String.format(Locale.CHINA, "出错了！错误代码：%d", ((ApiException) e).getCode());
            }
            onError(msg);
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case ERROR_CODE_BAD_REQUEST:
                case ERROR_CODE_FORBIDDEN:
                case ERROR_CODE_NOT_FOUND:
                case ERROR_CODE_SERVER_ERROR:
                    onError(MSG_SERVER_ERROR);
                    break;
                case ERROR_CODE_TIMEOUT:
                    onError(MSG_TIME_OUT);
                    break;
                default:
                    onError(MSG_NETWORK_ERROR);
                    break;
            }
        } else if (e instanceof SocketTimeoutException) {
            onError(MSG_TIME_OUT);
        } else if (e instanceof ConnectException) {
            onError(MSG_NETWORK_ERROR);
        } else if (e instanceof UnknownHostException) {
            onError(MSG_NETWORK_CONNECTION_ERROR);
        } else if (e instanceof SocketException) {
            onError(MSG_SERVER_ERROR);
        } else {
            onError(MSG_UNKNOWN_ERROR);
        }
    }

    protected abstract void onError(String msg);
}