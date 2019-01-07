package com.example.bw.presenter;


import com.example.bw.callback.ICallBack;
import com.example.bw.model.IModelImpl;
import com.example.bw.view.IView;

import java.util.Map;

public class IPresenterImpl implements IPresenter {
    private IModelImpl model;
    private IView iView;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        model = new IModelImpl();
    }

    @Override
    public void startRequest(String httpmodel,String url, Map<String, String> params, Class clazz) {
        model.iModelData(httpmodel,url, params, clazz, new ICallBack() {
            @Override
            public void onSuccess(Object data) {
                iView.getDataSuccess(data);
            }

            @Override
            public void onFail(String error) {
                iView.getDataFail(error);
            }
        });
    }

    public void onDetach() {
        if (model != null) {
            model = null;
        }
        if (iView != null) {
            iView = null;
        }
    }
}
