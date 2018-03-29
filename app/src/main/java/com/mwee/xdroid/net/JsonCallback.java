package com.mwee.xdroid.net;

import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mwee.xdroid.ClientApplication;
import com.mwee.xdroid.kit.AppKit;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;

import java.lang.reflect.ParameterizedType;
import java.net.UnknownHostException;
import cn.droidlover.xdroidbase.cache.DiskCache;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *  JsonCallBack 复写了onError和pareNetworkResponse方法
 *
 *  回调仅仅需要执行 onfail
 *                 onResponse
 *
 *
 *
 * @param <T>
 */
public abstract class JsonCallback<T> extends Callback<T> {

    private long expireTime = -1L;        //缓存时间
    private Class<T> entityClass;

    public JsonCallback() {
        this(-1L);
    }

    public JsonCallback(long expireTime) {
        this.expireTime = expireTime;
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        NetError error = null;
        if (e != null) {
           /* if (e instanceof UnknownHostException
                    && expireTime > -1) {
                *//**
                 * 无网络连接的时候  读取DisKCache中的数据
                 *//*
                String cacheKey = getCacheKey(call.request());
                String cacheContent = DiskCache.getInstance(ClientApplication.getContext()).get(cacheKey);

                if (!TextUtils.isEmpty(cacheContent)) {
                    try {
                        if (entityClass == String.class) {
                            onResponse((T) cacheContent, id);
                            return;
                        }
                        T model = GsonProvider.getInstance().getGson().fromJson(cacheContent, entityClass);
                        onResponse(model, id);
                        return;

                    } catch (Exception exception) {
                    }
                }
            }*/
            if (!(e instanceof NetError)) {
                if (e instanceof UnknownHostException) {
                    error = new NetError(e, NetError.NoConnectError);
                } else if (e instanceof JSONException
                        || e instanceof JsonParseException
                        || e instanceof JsonSyntaxException) {
                    error = new NetError(e, NetError.ParseError);
                } else {
                    error = new NetError(e, NetError.OtherError);
                }
            } else {
                error = (NetError) e;
            }
        }
        onFail(call, error, id);
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();

        //Cache
        if (expireTime > -1 && !TextUtils.isEmpty(string)) {
            /**
             * 网络数据解析成功 DiskCache缓存数据
             */
            if(ClientApplication.getContext() != null){
                String cacheKey = getCacheKey(response.request());
                DiskCache.getInstance(ClientApplication.getContext()).put(cacheKey, string, expireTime);
            }
        }

        if (entityClass == String.class) {
            return (T) string;
        }
        return GsonProvider.getInstance().getGson().fromJson(string, entityClass);
    }

    public abstract void onFail(Call call, NetError e, int id);

    /**
     * 获取缓存的key
     *
     * @param request
     * @return
     */
    private String getCacheKey(Request request) {
        String url = request.url().toString();
        RequestBody requestBody = request.body();
        if (requestBody != null) {
            MediaType mediaType = requestBody.contentType();
            if (mediaType != null) {
                if (AppKit.isText(mediaType)) {
                    String bodyStr = AppKit.bodyToString(request);
                    return new StringBuilder(url).append(bodyStr).toString();
                }
            }
        }
        return url;
    }
}
