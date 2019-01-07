package com.example.bw.model;



import com.example.bw.callback.ICallBack;

import java.util.Map;

public interface IModel {
    void iModelData(String model,String url, Map<String, String> map, Class aClass, ICallBack callBack);
}
