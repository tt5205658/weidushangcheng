package com.example.bw.model;


import android.support.v7.widget.RecyclerView;

import com.example.bw.callback.ICallBack;
import com.example.bw.utils.okhttputils.RetrofitManager;
import com.google.gson.Gson;


import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class IModelImpl<T> implements IModel {

    @Override
    public void iModelData(String model, String url, Map<String, String> params, final Class clazz, final ICallBack callBack) {
        switch (model) {
            case "post":
                setPost(url, params, clazz, callBack);
                break;
            case "get":
                setGet(url, clazz, callBack);
                break;
            case "postFormBody":
                break;
            case "delete":
                setDelete(url, clazz, callBack);
                break;
            case "put":
                setPut(url, params, clazz, callBack);
                break;
            case "imagePost":
                setImagePost(url,params, clazz, callBack);
                break;
        }


    }

    private void setPost(String url, Map<String, String> params, final Class clazz, final ICallBack callBack) {
        RetrofitManager.getInstance().post(url, params, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                if (callBack != null) {
                    Object o = new Gson().fromJson(data, clazz);
                    callBack.onSuccess(o);
                }
            }

            @Override
            public void onFail(String error) {
                if (callBack != null) {
                    callBack.onFail(error);
                }
            }
        });
    }

    private void setImagePost(String url, Map<String, String> params, final Class clazz, final ICallBack callBack) {
        RetrofitManager.getInstance().imagePost(url,params, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                String data1 = data;
                Object o = new Gson().fromJson(data1, clazz);
                callBack.onSuccess(o);
            }

            @Override
            public void onFail(String error) {
                String error1 = error;
                callBack.onFail(error);
            }
        });


    }

    private void setPut(String url, Map<String, String> params, final Class clazz, final ICallBack callBack) {
        RetrofitManager.getInstance().put(url, params, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                if (callBack != null) {
                    Object o = new Gson().fromJson(data, clazz);
                    callBack.onSuccess(o);
                }
            }

            @Override
            public void onFail(String error) {
                if (callBack != null) {
                    callBack.onFail(error);
                }
            }
        });
    }

    private void setDelete(String url, Class aClass, ICallBack iCallBack) {
        RetrofitManager.getInstance().delete(url, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                if (iCallBack != null) {
                    Object o = new Gson().fromJson(data, aClass);
                    iCallBack.onSuccess(o);
                }
            }

            @Override
            public void onFail(String error) {
                if (iCallBack != null) {
                    iCallBack.onFail(error);
                }
            }
        });
    }

    private void setGet(String url, Class aClass, ICallBack callBack) {
        RetrofitManager.getInstance().get(url, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                if (callBack != null) {
                    Object o = new Gson().fromJson(data, aClass);
                    callBack.onSuccess(o);
                }
            }

            @Override
            public void onFail(String error) {
                if (callBack != null) {
                    callBack.onFail(error);
                }
            }
        });
    }

}
