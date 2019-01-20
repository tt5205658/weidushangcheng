package com.example.bw.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bw.R;
import com.example.bw.adapter.fragmenthome.FragmentHomePopupOneAdapter;
import com.example.bw.adapter.fragmenthome.FragmentHomePopupTwoAdapter;
import com.example.bw.base.basefragment.BaseFragment;
import com.example.bw.bean.home.HomePopupOneBean;
import com.example.bw.bean.home.HomePopupTwoBean;
import com.example.bw.bean.home.HomeShoppingBean;
import com.example.bw.bean.home.HomeSwitchEventBean;
import com.example.bw.bean.home.SkitFragment;
import com.example.bw.fragment.circle.FragmentHomeHomepage;
import com.example.bw.fragment.circle.FragmentHomeShopping;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.graphics.Color.WHITE;
import static android.graphics.Color.YELLOW;

public class HomeFragment extends BaseFragment implements IView {
    @BindView(R.id.fragment_home_fragmentlayout)
    FrameLayout frameLayout;
    @BindView(R.id.fragment_home_head_edittext_search)
    EditText editTextSerch;
    @BindView(R.id.fragment_home_head_switche)
    ImageView imageViewSwitche;
    @BindView(R.id.fragment_home_head_textview_search)
    TextView textViewSearch;
    private Unbinder bind;
    private FragmentManager supportFragmentManager;
private boolean tag;
    private FragmentHomePopupOneAdapter oneAdapter;
    private FragmentHomePopupTwoAdapter twoAdapter;
    private IPresenterImpl iPresenter;
    private PopupWindow popupWindow;
    private FragmentTransaction fragmentTransaction;
    private FragmentHomeShopping homeShopping;
    @Override
    protected int setViewID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initButterKnife(View view) {
        bind = ButterKnife.bind(this, view);

    }

    @Override
    protected void initView() {
        tag=true;


        iPresenter = new IPresenterImpl(this);
        supportFragmentManager = getActivity().getSupportFragmentManager();
        initFragment(tag);
initimageViewSwitche();
    }

@Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
public void setFragment(SkitFragment fragment){

    //3 通过fragmentmanager获取fragment的事务管理对象
    fragmentTransaction = supportFragmentManager.beginTransaction();
    //4获取要显示的fragment
    FragmentHomeHomepage homepage = new FragmentHomeHomepage();


        fragmentTransaction.replace(R.id.fragment_home_fragmentlayout, homepage);

    fragmentTransaction.commit();
}
    @OnClick(R.id.fragment_home_head_textview_search)
    public void searchClick(){
    String s = editTextSerch.getText().toString();
    if(!TextUtils.isEmpty(s)){
        EventBus.getDefault().postSticky(new HomeSwitchEventBean(s,2));
        fragmentTransaction = supportFragmentManager.beginTransaction();
        homeShopping = new FragmentHomeShopping();
        fragmentTransaction.replace(R.id.fragment_home_fragmentlayout,homeShopping);
        fragmentTransaction.commit();
    }
}

    private void initimageViewSwitche() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.popup_home_switch,null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        RecyclerView oneRecycle = view.findViewById(R.id.popup_home_switch_onerecycle);
        RecyclerView twoRecycle = view.findViewById(R.id.popup_home_switch_tworecycle);
        LinearLayoutManager layoutManagertwo = new LinearLayoutManager(getActivity());
        layoutManagertwo.setOrientation(LinearLayoutManager.HORIZONTAL);
        oneRecycle.setLayoutManager(layoutManager);
       twoRecycle.setLayoutManager(layoutManagertwo);
        oneAdapter = new FragmentHomePopupOneAdapter(getActivity());
        twoAdapter = new FragmentHomePopupTwoAdapter(getActivity());
        oneRecycle.setAdapter(oneAdapter);
        twoRecycle.setAdapter(twoAdapter);

        oneAdapter.onCallBack(new FragmentHomePopupOneAdapter.popupCallBakc() {
            @Override
            public void callBack(String code) {
                iPresenter.startRequest(HttpModel.GET,"commodity/v1/findSecondCategory?firstCategoryId="+code,null,HomePopupTwoBean.class);
            }
        });

        twoAdapter.onCallBack(new FragmentHomePopupTwoAdapter.popupCallBakc() {
            @Override
            public void callBack(String code) {
                fragmentTransaction = supportFragmentManager.beginTransaction();
                homeShopping = new FragmentHomeShopping();
                fragmentTransaction.replace(R.id.fragment_home_fragmentlayout,homeShopping);
                fragmentTransaction.commit();
                EventBus.getDefault().postSticky(new HomeSwitchEventBean(code,1));
                Toast.makeText(getActivity(),code,Toast.LENGTH_SHORT).show();
            }
        });
        popupWindow = new PopupWindow(view,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        //popupWindow.showAtLocation(view,Gravity.CENTER_VERTICAL,0,-340);
    }

    @OnClick(R.id.fragment_home_head_switche)
    public void showPopup(){
        popupWindow.showAsDropDown(imageViewSwitche,100,12);
        Toast.makeText(getActivity(),"123",Toast.LENGTH_SHORT).show();
    }



    private void initFragment(boolean tag) {

        //3 通过fragmentmanager获取fragment的事务管理对象
        fragmentTransaction = supportFragmentManager.beginTransaction();
        //4获取要显示的fragment
        FragmentHomeHomepage homepage = new FragmentHomeHomepage();

        if(tag){
            fragmentTransaction.replace(R.id.fragment_home_fragmentlayout, homepage);
        }else{

        }
        fragmentTransaction.commit();
    }



    @Override
    protected void initData() {
        iPresenter.startRequest(HttpModel.GET,"commodity/v1/findFirstCategory",null,HomePopupOneBean.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
        iPresenter.onDetach();
    }

    @Override
    public void getDataSuccess(Object data) {
        if(data instanceof HomePopupOneBean){
            HomePopupOneBean data1 = (HomePopupOneBean) data;
            oneAdapter.setmList(data1.getResult());
        }
        if(data instanceof HomePopupTwoBean){
            HomePopupTwoBean data1 = (HomePopupTwoBean) data;
           twoAdapter.setmList(data1.getResult());
        }
    }

    @Override
    public void getDataFail(String error) {

    }
}
