package com.example.bw.presenter;

import java.util.Map;

public interface IPresenter {
    void startRequest(String model,String url, Map<String, String> params, Class clazz);
}
