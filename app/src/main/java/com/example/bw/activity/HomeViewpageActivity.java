package com.example.bw.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bw.R;
import com.example.bw.adapter.HomeViewPageAdapter;
import com.example.bw.base.baseactivity.BaseActivity;
import com.example.bw.bean.home.SkitFragment;
import com.example.bw.bean.orderform.SkipFromOrderFrom;
import com.example.bw.bean.orderform.SkipHome;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeViewpageActivity extends BaseActivity {
    @BindView(R.id.activity_home_viewpage)
    ViewPager activityHomeViewpage;
    @BindView(R.id.activity_home_bottom_radiobutton_homepage)
    RadioButton activityHomeBottomRadiobuttonHomepage;
    @BindView(R.id.activity_home_bottom_radiobutton_circle)
    RadioButton activityHomeBottomRadiobuttonCircle;
    @BindView(R.id.activity_home_bottom_radiobutton_shopping)
    RadioButton activityHomeBottomRadiobuttonShopping;
    @BindView(R.id.activity_home_bottom_radiobutton_ordeform)
    RadioButton activityHomeBottomRadiobuttonOrdeform;
    @BindView(R.id.activity_home_bottom_radiobutton_my)
    RadioButton activityHomeBottomRadiobuttonMy;
    @BindView(R.id.activity_home_radiogroup)
    RadioGroup activityHomeRadiogroup;
    private HomeViewPageAdapter viewPageAdapter;

    @Override
    protected int setViewID() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    //  EventBus.getDefault().register(this);
        viewPageAdapter = new HomeViewPageAdapter(getSupportFragmentManager());
        activityHomeViewpage.setAdapter(viewPageAdapter);
        activityHomeViewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        activityHomeRadiogroup.check(R.id.activity_home_bottom_radiobutton_homepage);
                        break;
                    case 1:
                        activityHomeRadiogroup.check(R.id.activity_home_bottom_radiobutton_circle);
                        break;
                    case 2:
                        activityHomeRadiogroup.check(R.id.activity_home_bottom_radiobutton_shopping);
                        break;
                    case 3:
                        activityHomeRadiogroup.check(R.id.activity_home_bottom_radiobutton_ordeform);
                        break;
                    case 4:
                        activityHomeRadiogroup.check(R.id.activity_home_bottom_radiobutton_my);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        activityHomeRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.activity_home_bottom_radiobutton_homepage:
                        activityHomeViewpage.setCurrentItem(0);
                     //   activityHomeBottomRadiobuttonHomepage.setChecked(true);
                        break;
                    case R.id.activity_home_bottom_radiobutton_circle:
                        activityHomeViewpage.setCurrentItem(1);
                  //      activityHomeBottomRadiobuttonCircle.setChecked(true);
                        break;
                    case R.id.activity_home_bottom_radiobutton_shopping:
                        activityHomeViewpage.setCurrentItem(2);
                  //      activityHomeBottomRadiobuttonShopping.setChecked(true);
                        break;
                    case R.id.activity_home_bottom_radiobutton_ordeform:
                        activityHomeViewpage.setCurrentItem(3);
                    //    activityHomeBottomRadiobuttonOrdeform.setChecked(true);
                        break;
                    case R.id.activity_home_bottom_radiobutton_my:
                        activityHomeViewpage.setCurrentItem(4);
                       // activityHomeBottomRadiobuttonMy.setChecked(true);
                        break;
                }
            }
        });
    }
    /*@Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void skip(SkipHome skipHome){
        activityHomeViewpage.setCurrentItem(3);
        EventBus.getDefault().removeAllStickyEvents();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void skipFromOrder(SkipFromOrderFrom skip){
        if(skip.getType()==1){
            //首页
            activityHomeViewpage.setCurrentItem(0);
        }else if(skip.getType()==2){
            //订单
            activityHomeViewpage.setCurrentItem(3);
        }
    }*/

    @Override
    protected void initData() {

    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){

            if((System.currentTimeMillis()-exitTime) > 2000){
                EventBus.getDefault().postSticky(new SkitFragment(true));
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
