package com.example.bw.activity.my;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.SystemUpdatePolicy;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bw.R;
import com.example.bw.base.baseactivity.BaseActivity;
import com.example.bw.bean.my.UserHeadPic;
import com.example.bw.bean.user.UpdataPwdBean;
import com.example.bw.bean.user.UpdateNameBean;
import com.example.bw.bean.user.UserDataBean;
import com.example.bw.presenter.IPresenterImpl;
import com.example.bw.utils.ImageFileUtil;
import com.example.bw.utils.okhttputils.HttpModel;
import com.example.bw.view.IView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyParticularsActivity extends BaseActivity implements IView {
    @BindView(R.id.myParticulars_myPic)
    ImageView myParticularsMyPic;
    @BindView(R.id.myParticulars_myName)
    TextView myParticularsMyName;
    @BindView(R.id.myParticulars_myPwd)
    TextView myParticularsMyPwd;
    private IPresenterImpl iPresenter;
    private String path = Environment.getExternalStorageDirectory() + "/image.png";

    @Override
    protected int setViewID() {
        return R.layout.myparticularsactivity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        iPresenter = new IPresenterImpl(this);
        iPresenter.startRequest(HttpModel.GET, "user/verify/v1/getUserById", null, UserDataBean.class);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.myParticulars_myPic, R.id.myParticulars_myName, R.id.myParticulars_myPwd})
    public void onViewClicked(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        switch (view.getId()) {
            case R.id.myParticulars_myPic:

                View popupView = LayoutInflater.from(this).inflate(R.layout.popupcamera, null);
                PopupWindow popupWindow = new PopupWindow(popupView, 200, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                popupWindow.showAsDropDown(myParticularsMyPic, 100, 12);
                popupView.findViewById(R.id.popupCamera_startCamera).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // 存到sdcard中
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(new File(path)));
                        //执行
                        startActivityForResult(intent, 100);
                        popupWindow.dismiss();
                    }
                });
                popupView.findViewById(R.id.popupCamera_startPhoto).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //加载相册
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        //设置图片格式
                        intent.setType("image/*");
                        //执行
                        startActivityForResult(intent, 300);
                        popupWindow.dismiss();
                    }
                });
                popupView.findViewById(R.id.popupCamera_cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });


                break;
            case R.id.myParticulars_myName:
                View alertView = View.inflate(MyParticularsActivity.this, R.layout.alertdialog_name, null);
                alertDialog.setView(alertView);
                alertDialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText newName = alertView.findViewById(R.id.doalogName);

                        String trim = newName.getText().toString().trim();
                        if (!trim.isEmpty()) {
                            Map<String, String> map = new HashMap<>();
                            map.put("nickName", trim);
                            iPresenter.startRequest(HttpModel.PUT, "user/verify/v1/modifyUserNick", map, UpdateNameBean.class);
                            dialog.dismiss();
                        }
                    }
                });
                alertDialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
                break;
            case R.id.myParticulars_myPwd:
                View alertPwdView = View.inflate(MyParticularsActivity.this, R.layout.alertdialog_pwd, null);
                alertDialog.setView(alertPwdView);
                alertDialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText oldPwd = alertPwdView.findViewById(R.id.oldPwd);
                        EditText newPwd = alertPwdView.findViewById(R.id.newPwd);

                        String oldPwds = oldPwd.getText().toString().trim();
                        String newPwds = newPwd.getText().toString().trim();
                        if (!oldPwds.isEmpty() && !newPwds.isEmpty()) {
                            Map<String, String> map = new HashMap<>();
                            map.put("oldPwd", oldPwds);
                            map.put("newPwd", newPwds);
                            iPresenter.startRequest(HttpModel.PUT, "user/verify/v1/modifyUserPwd", map, UpdataPwdBean.class);
                            dialog.dismiss();
                        }
                    }
                });
                alertDialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
                break;
        }
    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof UserDataBean) {
            UserDataBean data1 = (UserDataBean) data;
            Glide.with(this).load(data1.getResult().getHeadPic()).into(myParticularsMyPic);
            myParticularsMyName.setText(data1.getResult().getNickName());
            myParticularsMyPwd.setText(data1.getResult().getPassword() + "");
        }
        if (data instanceof UpdateNameBean) {
            UpdateNameBean data1 = (UpdateNameBean) data;
            if (data1.getMessage().equals("修改成功")) {
                finish();
            }
        }
        if (data instanceof UserHeadPic) {
            UserHeadPic data1 = (UserHeadPic) data;
            Glide.with(MyParticularsActivity.this).load(data1.getHeadPath()).into(myParticularsMyPic);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //相机
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //调取裁剪功能
            Intent intent = new Intent("com.android.camera.action.CROP");
            //将图片设置给裁剪
            intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
            //设置是否支持裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置显示大小
            intent.putExtra("outputX", 50);
            intent.putExtra("outputY", 50);
            //将图片返回给data
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 200);
        }
        //相册
        if (requestCode == 300 && resultCode == RESULT_OK) {
            //获取相册路径
            Uri uri = data.getData();
            //调取裁剪功能
            Intent intent = new Intent("com.android.camera.action.CROP");
            //将图片设置给裁剪
            intent.setDataAndType(uri, "image/*");
            //设置是否支持裁剪
            intent.putExtra("CROP", true);
            //设置框高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置显示大小
            intent.putExtra("outputX", 50);
            intent.putExtra("outputY", 50);
            //将图片返回给data
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 200);
        }
        if (requestCode == 200 && resultCode == RESULT_OK) {
            Bitmap bitmap = data.getParcelableExtra("data");


            Uri mImageCaptureUri = data.getData();

            Bitmap photoBmp = null;

            if (mImageCaptureUri != null) {

                try {
                    photoBmp = getBitmapFormUri( mImageCaptureUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
           // myParticularsMyPic.setImageBitmap(photoBmp);

            try {
                ImageFileUtil.setBitmap(bitmap,fildPath,50);
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(MyParticularsActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
            Map<String,String> map = new HashMap<>();
            map.put("image",fildPath);
            //myProfileSimple.setImageURI();
            iPresenter.startRequest(HttpModel.IMAGE_POST,"user/verify/v1/modifyHeadPic",map,UserHeadPic.class);
        }


    }
    private String fildPath = Environment.getExternalStorageDirectory() + "/file.png";
    public void saveBitmapFile(Bitmap bitmap)
    {
        File file=new File(fildPath);//将要保存图片的路径
        try{
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bos.flush();
        bos.close();
    }catch (IOException e){
        e.printStackTrace();
    }
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public Bitmap getBitmapFormUri( Uri uri) throws FileNotFoundException, IOException {
        InputStream input = getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);//再进行质量压缩
    }


    @Override
    public void getDataFail(String error) {

    }
}
