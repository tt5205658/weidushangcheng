package com.example.bw.callback;

public interface ICallBack<T> {
    void onSuccess(T data);
    void onFail(String error);
}
