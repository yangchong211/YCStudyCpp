package cn.ycbjie.ycaudioplayer.api.http;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import java.io.IOException;

import okhttp3.Response;

public abstract class JsonCallback<T> extends Callback<T> {

    private Class<T> mClass;
    private Gson mGson;

    JsonCallback(Class<T> clazz) {
        this.mClass = clazz;
        mGson = new Gson();
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws IOException {
        try {
            String jsonString = response.body().string();
            return mGson.fromJson(jsonString, mClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
