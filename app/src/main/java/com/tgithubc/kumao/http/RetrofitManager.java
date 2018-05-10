package com.tgithubc.kumao.http;

import android.util.Log;

import com.tgithubc.kumao.BuildConfig;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by tc :)
 */
public class RetrofitManager {

    private static final String TAG = "RetrofitManager";

    private static final String URL_HEADER_BMOB = "bmob";
    private static final String URL_HEADER_MUSIC = "music";

    private static final String BASE_URL_MUSIC = "http://tingapi.ting.baidu.com/v1/restserver/";
    private static final String BASE_URL_BMOB = "bmob";

    private static final String USER_AGENT = "User-Agent";
    private static final String HEADER_USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)";

    private static final long TIME_OUT = 5000;
    private Retrofit mRetrofit;

    public static RetrofitManager getInstance() {
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    private RetrofitManager() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_MUSIC)
                //.addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(initOkHttpClient())
                .build();
    }

    public <T> T createService(Class<T> service) {
        return mRetrofit.create(service);
    }

    private OkHttpClient initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new BaseUrlInterceptor())
                .addInterceptor(new HeaderInterceptor());
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new LoggingInterceptor());
        }
        return builder.build();
    }

    private class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.currentTimeMillis();
            Log.d(TAG, "Request url： " + request.url());
            Response response = chain.proceed(request);
            long t2 = System.currentTimeMillis();
            //response.body().string()之后，response中的流会被关闭，需要创建出一个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            Log.d(TAG, "Response  cost:" + (t2 - t1)
                    + ",responseBody" + responseBody.string());
            return response;
        }
    }

    private class BaseUrlInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //从request中通过给定的键url_name获取headers
            List<String> headerValues = request.headers("url_name");
            if (headerValues == null || headerValues.isEmpty()) {
                return chain.proceed(request);
            }
            Request.Builder builder = request.newBuilder();
            builder.removeHeader("url_name");
            //根据head处理BaseUrl
            String headerValue = headerValues.get(0);
            HttpUrl newBaseUrl = null;
            if (URL_HEADER_MUSIC.equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(BASE_URL_MUSIC);
            } else if (URL_HEADER_BMOB.equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(BASE_URL_BMOB);
            }
            if (newBaseUrl == null) {
                return chain.proceed(request);
            }
            //从request中获取原有的url实例oldHttpUrl
            HttpUrl oldHttpUrl = request.url();
            //重建url，修改需要修改的url部分
            HttpUrl.Builder httpBuilder = oldHttpUrl
                    .newBuilder()
                    .scheme(newBaseUrl.scheme())
                    .host(newBaseUrl.host())
                    .port(newBaseUrl.port());
            if (URL_HEADER_MUSIC.equals(headerValue)) {
                httpBuilder.addQueryParameter("from", "qianqian");
                httpBuilder.addQueryParameter("version", "5.6.3.0");
                httpBuilder.addQueryParameter("format", "json");
            }
            //重建request返回
            return chain.proceed(builder.url(httpBuilder.build()).build());
        }
    }

    private class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request newRequest = chain.request().newBuilder()
                    .removeHeader(USER_AGENT)
                    .addHeader(USER_AGENT, HEADER_USER_AGENT)
                    .build();
            return chain.proceed(newRequest);
        }
    }
}
