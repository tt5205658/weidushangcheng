package com.example.bw.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bw.bean.user.UserRegisterBean;
import com.example.bw.MainActivity;
import com.example.bw.R;
import com.example.bw.base.baseactivity.BaseActivity;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.Apis;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.utils.regularutils.RegularUtil;
import com.example.bw.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class UserRegisterActivity extends BaseActivity implements IView  {
    @BindView(R.id.activity_register_edittext_username)
    EditText activityRegisterEdittextUsername;
    @BindView(R.id.activity_register_edittext_verificationcode)
    EditText activityRegisterEdittextVerificationcode;
    @BindView(R.id.activity_register_get_verificationcode)
    TextView activityRegisterGetVerificationcode;
    @BindView(R.id.activity_register_edittext_userpassword)
    EditText activityRegisterEdittextUserpassword;
    @BindView(R.id.activity_register_edittext_pwdhide)
    ImageView activityRegisterEdittextPwdhide;
    @BindView(R.id.activity_register_text_login)
    TextView activityRegisterTextLogin;
    @BindView(R.id.activity_register_button_login)
    Button activityRegisterButtonLogin;

    private boolean isHide=false;
    private IPresenterImpl iPresenter;
    private String user;

    @Override
    protected int setViewID() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        iPresenter = new IPresenterImpl(this);
    }

    @Override
    protected void initData() {

    }



    @OnClick({R.id.activity_register_get_verificationcode, R.id.activity_register_edittext_pwdhide, R.id.activity_register_text_login, R.id.activity_register_button_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_register_get_verificationcode:
                sendCode(UserRegisterActivity.this);

                break;
            case R.id.activity_register_edittext_pwdhide:
                isHide=!isHide;
                if(isHide){
                    //如果选中，显示密码
                    activityRegisterEdittextUserpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    activityRegisterEdittextUserpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.activity_register_text_login:
                startActivity(new Intent(UserRegisterActivity.this,MainActivity.class));
                finish();
                break;
            case R.id.activity_register_button_login:

                //点击注册按钮验证密码
                onClickLogin();

                break;
        }
    }

    private void onClickLogin() {
        user = activityRegisterEdittextUsername.getText().toString();
        String pwd = activityRegisterEdittextUserpassword.getText().toString();
        String verificationcode = activityRegisterEdittextVerificationcode.getText().toString();
        if(verificationcode.equals("验证成功")){
           if(isUserNameandPwdOk(user, pwd)){
               Map<String,String>map = new HashMap<>();
               map.put("phone", user);
               map.put("pwd",pwd);
               iPresenter.startRequest(HttpModel.POST,Apis.POST_URL_USER_REGISTER,map,UserRegisterBean.class);
           }
        }else{
            Toast.makeText(UserRegisterActivity.this,"请点击右侧获取验证码",Toast.LENGTH_LONG).show();
        }
    }
    public boolean isUserNameandPwdOk(String userName, String userPwa) {
        if (!RegularUtil.isMobile(userName)) {
            Toast.makeText(UserRegisterActivity.this, "请检查手机号是否正确!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!RegularUtil.isPassword(userPwa)) {
            Toast.makeText(UserRegisterActivity.this, "密码格式错误,最短6位密码", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    @Override
    public  void getDataSuccess(Object data) {
        if(data instanceof UserRegisterBean){
            UserRegisterBean userRegisterBean = (UserRegisterBean) data;
            String message = userRegisterBean.getMessage();
            if(message.equals("注册成功")){
                callBackData.setPhoneData(user);
                startActivity(new Intent(UserRegisterActivity.this,MainActivity.class));

            }else{
                Toast.makeText(UserRegisterActivity.this,message,Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetach();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    static CallBackData callBackData;
    public  interface CallBackData{
        void setPhoneData(String phone);
    }
    public static void phoneCallBackData(CallBackData data){
        callBackData = data;
    }
    @Override
    public void getDataFail(String error) {

    }

    /*
     * create tang 2018 12 29
     * 短信验证*/
    public void sendCode(Context context) {
        RegisterPage page = new RegisterPage();
        //如果使用我们的ui，没有申请模板编号的情况下需传null
        page.setTempCode(null);
        page.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 处理成功的结果
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country"); // 国家代码，如“86”
                    String phone = (String) phoneMap.get("phone"); // 手机号码，如“13800138000”
                    // TODO 利用国家代码和手机号码进行后续的操作
                    activityRegisterEdittextVerificationcode.setText("验证成功");
                } else {
                    // TODO 处理错误的结果
                    activityRegisterEdittextVerificationcode.setText("验证失败");
                }
            }
        });
        page.show(context);
    }

    public static class HomePageActivity {
    }
}
