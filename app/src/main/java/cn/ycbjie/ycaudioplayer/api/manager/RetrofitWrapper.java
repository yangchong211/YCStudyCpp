package cn.ycbjie.ycaudioplayer.api.manager;

import android.annotation.SuppressLint;

import com.google.gson.Gson;

import java.io.File;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.ycbjie.ycaudioplayer.BuildConfig;
import cn.ycbjie.ycaudioplayer.api.http.HttpInterceptor;
import cn.ycbjie.ycaudioplayer.constant.Constant;
import cn.ycbjie.ycaudioplayer.utils.InterceptorUtils;
import cn.ycbjie.ycaudioplayer.utils.JsonUtils;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/1/18
 * 描    述：RetrofitWrapper
 * 修订历史：
 * ================================================
 */
public class RetrofitWrapper {

    private static RetrofitWrapper instance;
    private Retrofit mRetrofit;
    private final OkHttpClient.Builder builder;


    public  static RetrofitWrapper getInstance(String url){
        if(instance==null){
            //synchronized 避免同时调用多个接口，导致线程并发
            synchronized (RetrofitWrapper.class){
                instance = new RetrofitWrapper(url);
            }
        }
        return instance;
    }


    private RetrofitWrapper(String url) {
        builder = new OkHttpClient.Builder();

        //拦截日志，依赖
        builder.addInterceptor(new HttpInterceptor());
        builder.addInterceptor(InterceptorUtils.getHttpLoggingInterceptor(true));
        OkHttpClient build = builder.build();

        //拦截日志，自定义拦截日志
        //builder.addInterceptor(new LogInterceptor("YC"));

        //添加请求头拦截器
        //builder.addInterceptor(InterceptorUtils.getRequestHeader());

        //添加网络缓存缓存
        //创建Cache
        if(BuildConfig.IS_DEBUG){
            File httpCacheDirectory = new File("OkHttpCache");
            Cache cache = new Cache(httpCacheDirectory, Constant.CACHE_MAXSIZE);
            builder.cache(cache);
            //添加网络拦截器
            builder.addNetworkInterceptor(InterceptorUtils.getCacheInterceptor());
            builder.addInterceptor(InterceptorUtils.getCacheInterceptor());
        }


        //添加统一请求拦截器
        //builder.addInterceptor(InterceptorUtils.commonParamsInterceptor());
        //添加网络缓存拦截器，网络连接时请求服务器，否则从本地缓存中获取
        builder.addInterceptor(InterceptorUtils.addNetWorkInterceptor());

        //添加自定义CookieJar
        //InterceptorUtils.addCookie(builder);

        // Install the all-trusting trust manager
        initSSL();
        //设置读取超时时间，连接超时时间，写入超时时间值
        initTimeOut();
        if(BuildConfig.IS_DEBUG){
            //不需要错误重连
            builder.retryOnConnectionFailure(false);
        }else {
            //错误重连
            builder.retryOnConnectionFailure(true);
        }


        //获取实例
        mRetrofit = new Retrofit
                //设置OKHttpClient,如果不设置会提供一个默认的
                .Builder()
                //设置baseUrl
                .baseUrl(url)
                //添加Gson转换器
                .addConverterFactory(GsonConverterFactory.create(JsonUtils.getJson()))
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(build)
                .build();
    }


    public <T> T create(final Class<T> service) {
        return mRetrofit.create(service);
    }


    private void initSSL() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @SuppressLint("TrustAllX509TrustManager")
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @SuppressLint("TrustAllX509TrustManager")
                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }};

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initTimeOut() {
        builder.readTimeout(20000, TimeUnit.SECONDS);
        builder.connectTimeout(10000, TimeUnit.SECONDS);
        builder.writeTimeout(20000, TimeUnit.SECONDS);
    }

}