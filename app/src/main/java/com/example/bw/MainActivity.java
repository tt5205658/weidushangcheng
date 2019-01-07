package com.example.bw;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bw.activity.HomeViewpageActivity;
import com.example.bw.bean.user.UserLoginBean;
import com.example.bw.activity.UserRegisterActivity;
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

public class MainActivity extends BaseActivity implements IView {


    @BindView(R.id.activity_main_edittext_username)
    EditText activityMainEdittextUsername;
    @BindView(R.id.activity_main_edittext_userpassword)
    EditText activityMainEdittextUserpassword;
    @BindView(R.id.activity_main_edittext_pwdhide)
    ImageView activityMainEdittextPwdhide;
    @BindView(R.id.activity_main_checkbox_remember_pwd)
    CheckBox activityMainCheckboxRememberPwd;
    @BindView(R.id.activity_main_text_registered)
    TextView activityMainTextRegistered;
    @BindView(R.id.activity_main_button_login)
    Button activityMainButtonLogin;
    @BindView(R.id.activity_main_layout_checkbox)
    ConstraintLayout activityMainLayoutCheckbox;
    private IPresenterImpl iPresenter;
    private boolean checked;
    private SharedPreferences.Editor edit;
    private String user;
    private String pwd;
private boolean isHide = false;
    private SharedPreferences sharedPreferencesUserID;

    @Override
    protected int setViewID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //初始化
        sharedPreferencesUserID = getSharedPreferences("UserID",MODE_PRIVATE);
        ButterKnife.bind(this);
        iPresenter = new IPresenterImpl(this);
        initSharedPreferences();

    }

    private void initUserIdSharedPreferences(String userID,String sessionID) {

        SharedPreferences.Editor editUserID = sharedPreferencesUserID.edit();
        editUserID.putString("userId",userID);
        editUserID.putString("sessionId",sessionID);
        editUserID.commit();
    }

    private void initRegisterCallBack() {
        UserRegisterActivity.phoneCallBackData(new UserRegisterActivity.CallBackData() {
            @Override
            public void setPhoneData(String phone) {
                edit.putBoolean("remember_callback_phone", true);
                edit.putString("remember_callback_user",phone);
                edit.commit();
                activityMainCheckboxRememberPwd.setChecked(false);
                activityMainEdittextUsername.setText(phone);
                activityMainEdittextUserpassword.setText("");
            }
        });
    }

    //设置保存密码
    private void initSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
        edit = sharedPreferences.edit();
        boolean remember_the_password = sharedPreferences.getBoolean("remember_the_password", false);
        if (remember_the_password) {
            activityMainCheckboxRememberPwd.setChecked(remember_the_password);
            String spname = sharedPreferences.getString("name", null);
            String sppwd = sharedPreferences.getString("pwd", null);
            activityMainEdittextUsername.setText(spname);
            activityMainEdittextUserpassword.setText(sppwd);
        }
        boolean remember_callback_phone = sharedPreferences.getBoolean("remember_callback_phone", false);
        if(remember_callback_phone){
            String remember_callback_user = sharedPreferences.getString("remember_callback_user", null);
            activityMainEdittextUsername.setText(remember_callback_user);
            activityMainEdittextUserpassword.setText("");
            activityMainCheckboxRememberPwd.setChecked(false);
        }

    }




    @Override
    protected void initData() {
        initRegisterCallBack();
    }


    @OnClick({R.id.activity_main_checkbox_remember_pwd, R.id.activity_main_button_login,R.id.activity_main_edittext_pwdhide, R.id.activity_main_text_registered})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_main_checkbox_remember_pwd:
                CheckBox view1 = (CheckBox) view;
                if (view1.isChecked()) {
                    Toast.makeText(MainActivity.this, "记住密码", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "取消记住密码", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.activity_main_button_login:
                user = activityMainEdittextUsername.getText().toString();
                pwd = activityMainEdittextUserpassword.getText().toString();
                boolean userNameandPwdOk = isUserNameandPwdOk(user, pwd);
                checked = activityMainCheckboxRememberPwd.isChecked();
                //ToDo 请求登录接口验证是否成功,成功判断是否记住密码
                if (userNameandPwdOk) {
                    Map<String, String> map = new HashMap<>();
                    map.put("phone", user);
                    map.put("pwd", pwd);
                    iPresenter.startRequest(HttpModel.POST,Apis.POST_URL_USER_LOGIN, map, UserLoginBean.class);
                } else {
                    Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.activity_main_edittext_pwdhide:
                isHide=!isHide;
                if(isHide){
                    //如果选中，显示密码
                    activityMainEdittextUserpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    activityMainEdittextUserpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.activity_main_text_registered:
                startActivity(new Intent(MainActivity.this,UserRegisterActivity.class));
                finish();
                break;
        }
    }

    //
    public boolean isUserNameandPwdOk(String userName, String userPwa) {
        if (!RegularUtil.isMobile(userName)) {
            Toast.makeText(MainActivity.this, "请检查手机号是否正确!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!RegularUtil.isPassword(userPwa)) {
            Toast.makeText(MainActivity.this, "密码格式错误,最短6位密码", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof UserLoginBean) {
            UserLoginBean data1 = (UserLoginBean) data;
            if (data1.getMessage().equals("登录成功")) {
                initUserIdSharedPreferences(data1.getResult().getUserId()+"",data1.getResult().getSessionId()+"");
                isRememberPwd();
                initUserId(data1);
                Intent intent = new Intent(MainActivity.this,HomeViewpageActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(MainActivity.this, data1.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
//记住id
    private void initUserId(UserLoginBean data1) {
        edit.putString("userId",data1.getResult().getUserId()+"");
        edit.putString("sessionId",data1.getResult().getSessionId()+"");
        edit.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        edit.putBoolean("remember_callback_phone",false);
        edit.commit();
    }

    //执行判断是否记住密码
    private void isRememberPwd() {
        if (checked) {
            edit.putBoolean("remember_the_password", true);
            edit.putString("name", user);
            edit.putString("pwd", pwd);
            edit.commit();
        } else {
            edit.clear();
        }
    }

    @Override
    public void getDataFail(String error) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
