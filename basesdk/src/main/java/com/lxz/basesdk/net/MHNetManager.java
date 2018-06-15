package com.lxz.basesdk.net;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MHNetManager {

    private static MHNetManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private Gson mGson;

    public static MHNetManager getInstance() {
        if (mInstance == null) {
            synchronized (MHNetManager.class) {
                if (mInstance == null) {
                    mInstance = new MHNetManager();
                }
            }
        }
        return mInstance;
    }

    private MHNetManager() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(5, TimeUnit.SECONDS);
        okHttpClient.readTimeout(5, TimeUnit.SECONDS);
//        okHttpClient.cookieJar(new CookieJar() {
//
//            private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
//
//            @Override
//            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//                cookieStore.put(url, cookies);
//            }
//
//            @Override
//            public List<Cookie> loadForRequest(HttpUrl url) {
//                return null;
//            }
//        });
        mOkHttpClient = okHttpClient.build();
        mDelivery = new Handler(Looper.getMainLooper());
        mGson = new Gson();
    }


    /**
     * 异步的get请求
     *
     * @param url
     * @param callback
     */
    private void _getAsync(String url, final ResultCallback callback) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        deliveryResult(callback, request);
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsync(String url, final ResultCallback callback, Param... params) {
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request);
    }

    /**
     * 异步的post请求
     *
     * @param url
     * @param callback
     * @param params
     */
    private void _postAsync(String url, final ResultCallback callback, Map<String, String> params) {
        Param[] paramsArr = map2Params(params);
        Request request = buildPostRequest(url, paramsArr);
        deliveryResult(callback, request);
    }

    private void _postAsync(String url, final ResultCallback callback, String paramsStr) {
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), paramsStr);
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        deliveryResult(callback, request);
    }

    private Param[] map2Params(Map<String, String> params) {
        if (params == null) return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries) {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }

    private void deliveryResult(final ResultCallback callback, Request request) {

        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                sendFailCallback(callback, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String string = response.body().string();
                    if (callback.mType == String.class) {
                        sendSuccessCallBack(callback, string);
                    } else {
                        Object o = mGson.fromJson(string, callback.mType);
                        sendSuccessCallBack(callback, o);
                    }
                } catch (IOException e) {
                    sendFailCallback(callback, e);
                } catch (com.google.gson.JsonParseException e) { // Json解析的错误
                    sendFailCallback(callback, e);
                }
            }
        });
    }

    private void sendFailCallback(final ResultCallback callback, final Exception e) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onFailure(e);
                }
            }
        });
    }

    private void sendSuccessCallBack(final ResultCallback callback, final Object obj) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onSuccess(obj);
                }
            }
        });
    }

    private Request buildPostRequest(String url, Param[] params) {
        if (params == null) {
            params = new Param[0];
        }
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (Param param : params) {
            formBodyBuilder.add(param.key, param.value);
        }
        RequestBody requestBody = formBodyBuilder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    public static abstract class ResultCallback<T> {
        Type mType;

        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        /**
         * 请求成功回调
         *
         * @param response
         */
        public abstract void onSuccess(T response);

        /**
         * 请求失败回调
         *
         * @param e
         */
        public abstract void onFailure(Exception e);
    }

    /**
     * post请求参数类
     */
    public static class Param {

        String key;
        String value;

        public Param() {

        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }


    //*************对外公布的方法************

    //异步get请求
    public static void getAsyn(String url, ResultCallback callback) {
        getInstance()._getAsync(url, callback);
    }

    //异步的post请求
    public static void postAsyn(String url, final ResultCallback callback, Param... params) {
        getInstance()._postAsync(url, callback, params);
    }

    //异步的post请求
    public static void postAsyn(String url, final ResultCallback callback, Map<String, String> params) {
        getInstance()._postAsync(url, callback, params);
    }

    //异步的post请求
    public static void postAsyn(String url, final ResultCallback callback, String paramsStr) {
        getInstance()._postAsync(url, callback, paramsStr);
    }
}
