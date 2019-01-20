package com.example.bw.utils.okhttputils;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.bw.App;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager  {
    BaseApi baseApi;
    private final String BASE_API="http://mobile.bwstudent.com/small/";
    private static RetrofitManager mRetrofitManager;
    public static synchronized RetrofitManager getInstance(){
        if(mRetrofitManager==null){
            mRetrofitManager=new RetrofitManager();
        }
        return mRetrofitManager;
    }
    public RetrofitManager(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        //取出保存的userid,sessionid
                        SharedPreferences sharedPreferences =App.getApplication().getSharedPreferences("UserID",Context.MODE_PRIVATE);
                        String userId = sharedPreferences.getString("userId", "");
                        String sessionId = sharedPreferences.getString("sessionId", "");
                        //重新构造请求
                        Request.Builder builder1 = request.newBuilder();
                        //把原来的请求参数原样放进去
                        builder1.method(request.method(),request.body());
                        //添加自己的参数
                        if(!TextUtils.isEmpty(userId)&&!TextUtils.isEmpty(sessionId)){
                            builder1.addHeader("userId",userId);
                            builder1.addHeader("sessionId",sessionId);
                        }
                        Request build = builder1.build();
                        return chain.proceed(build);
                    }
                });
                builder.connectTimeout(30,TimeUnit.SECONDS);
                builder.writeTimeout(30,TimeUnit.SECONDS);
                builder.readTimeout(30,TimeUnit.SECONDS);
        OkHttpClient build = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API)
                .client(build)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        baseApi= retrofit.create(BaseApi.class);
    }
    public Map<String, RequestBody> generateRequestBody(Map<String, String> requestDataMap) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String key : requestDataMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    requestDataMap.get(key) == null ? "" : requestDataMap.get(key));
            requestBodyMap.put(key, requestBody);
        }
        return requestBodyMap;
    }


    /**
     *上传头像
     * */
    public static MultipartBody filesMultipar(Map<String,String> map){
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for(Map.Entry<String,String> entry:map.entrySet()){
            File file = new File(entry.getValue());
            builder.addFormDataPart(entry.getKey(),"tp.png",
                    RequestBody.create(MediaType.parse("multipart/form-data"),file));
        }
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }
    public void imagePost(String url, Map<String,String> map,HttpListener listener){
        if(map == null){
            map = new HashMap<>();
        }
        MultipartBody multipartBody = filesMultipar(map);
        baseApi.imagePost(url,multipartBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    public RetrofitManager get(String url,HttpListener listener1) {

        baseApi.get(url)
                //后台执行在哪个线程中
                .subscribeOn(Schedulers.io())
                //最终完成后执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())
                //设置我们的rxJava
                .subscribe(getObserver(listener1));

        return mRetrofitManager;
    }

public RetrofitManager delete(String url,HttpListener listener1){
        baseApi.delete(url)
                //后台执行在哪个线程中
                .subscribeOn(Schedulers.io())
                //最终完成后执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())
                //设置我们的rxJava
                .subscribe(getObserver(listener1));
        return mRetrofitManager;
}
    /**
     * 表单post请求
     */
    public RetrofitManager postFormBody(String url, Map<String, RequestBody> map,HttpListener listener1) {
        if (map == null) {
            map = new HashMap<>();
        }

        baseApi.postFormBody(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener1));
        return mRetrofitManager;
    }


    /**
     * 普通post请求
     */
    public void post(String url, Map<String, String> map,HttpListener listener1) {
        if (map == null) {
            map = new HashMap<>();
        }
        baseApi.post(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener1));

    }

    /*public void imagePost( String path, HttpListener listener1){
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
            baseApi.imagePost(part)
                    .enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, retrofit2.Response response) {
                            String s = response.body().toString();
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            String message = t.getMessage();
                        }
                    });
    }*/

    public void put(String url, Map<String, String> map,HttpListener listener1) {
        if (map == null) {
            map = new HashMap<>();
        }
        baseApi.put(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener1));

    }
    private Observer getObserver(HttpListener listener1){
        Observer observer = new Observer<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String data = responseBody.string();
                    if (listener1 != null) {
                        listener1.onSuccess(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (listener1 != null) {
                        listener1.onFail(e.getMessage());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (listener1 != null) {
                    listener1.onFail(e.getMessage());
                }
            }

            @Override
            public void onCompleted() {

            }
        };
        return observer;
    }


    private HttpListener listener;



    public interface HttpListener {
        void onSuccess(String data);

        void onFail(String error);
    }
}


