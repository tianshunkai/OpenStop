package com.example.openstop.openstop;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.citywheel.CityPickerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static javax.xml.transform.OutputKeys.ENCODING;

public class Activity_gerenxx extends AppCompatActivity implements View.OnClickListener {

    public Context context = Activity_gerenxx.this;
    String msg = "test";

    // numberpicker1 简单的NumberPicker的操作
    private NumberPicker numberpicker1;
    // numberpicker2,numberpicker3是星座month和day
    private NumberPicker numberpicker2;
    private NumberPicker numberpicker3;



    private TextView text;
    private TextView text1;
    private int num;
    private AlertDialog.Builder dialog;

    private TextView tv1;
    private TextView tv2;
    int month =1;
    int day =1;

    private ImageView iv_photo;
    private Bitmap head;// 头像Bitmap
    private String path ;// sd路径
    private TextView nicheng;
    private TextView tel;

    private String pro;
    private String city;
    private String dis;

    private String usepath;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenxx);



        sharedPreferences=getSharedPreferences("mager", MODE_PRIVATE);
        usepath=sharedPreferences.getString("user","");
        path = "/sdcard/myHead/"+usepath+"/";

        initView();
        initnicheng();
        initListener();
        inituser();
        inittel();
        initbday();
        initcity();

        Button xx = (Button) findViewById(R.id.t_c);
        xx.setOnClickListener(new Button.OnClickListener() {//创建监听
            public void onClick(View v) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("islogin", false);
                editor.commit();
                Intent inten = new Intent(Activity_gerenxx.this, MainActivity.class);
                startActivity(inten);

            }
        });


        nicheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(Activity_gerenxx.this, Activity_xiugainicheng.class);
                startActivity(inten);
                finish();
            }
        });

        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(Activity_gerenxx.this, Activity_xiugaitel.class);
                startActivity(inten);
                finish();
            }
        });

    }

    private void initcity() {
        tv2=(TextView)findViewById(R.id.grxx_city);


        pro="";
        try {
            FileInputStream fin = openFileInput(usepath+"pro");
            //获取文件长度
            int lenght = fin.available();
            byte[] buffer = new byte[lenght];
            fin.read(buffer);
            //将byte数组转换成指定格式的字符串
            pro=new String(buffer);

        } catch (Exception e) {
            e.printStackTrace();
        }

        city="";
        try {
            FileInputStream fin = openFileInput(usepath+"city");
            //获取文件长度
            int lenght = fin.available();
            byte[] buffer = new byte[lenght];
            fin.read(buffer);
            //将byte数组转换成指定格式的字符串
            city=new String(buffer);

        } catch (Exception e) {
            e.printStackTrace();
        }

        dis="";
        try {
            FileInputStream fin = openFileInput(usepath+"dis");
            //获取文件长度
            int lenght = fin.available();
            byte[] buffer = new byte[lenght];
            fin.read(buffer);
            //将byte数组转换成指定格式的字符串
            dis=new String(buffer);

        } catch (Exception e) {
            e.printStackTrace();
        }

        tv2.setText(" "+pro+" - "+city+" - "+dis);

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);*/
                selectAddress(pro,city,dis);//调用CityPicker选取区域

                //}
            }
        });
    }


    private void initbday() {
        tv1=(TextView)findViewById(R.id.grxx_bday);

        String result="";
        try {
            FileInputStream fin = openFileInput(usepath+"bday");
            //获取文件长度
            int lenght = fin.available();
            byte[] buffer = new byte[lenght];
            fin.read(buffer);
            //将byte数组转换成指定格式的字符串
            result=new String(buffer);

        } catch (Exception e) {
            e.printStackTrace();
        }
        tv1.setText(result);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = createLoadingDialog(Activity_gerenxx.this, "test");
                dialog.create().show();
            }
        });
    }

    private void inittel() {
        tel=(TextView)findViewById(R.id.grxx_tel);
        String result="";
        try {
            FileInputStream fin = openFileInput(usepath+"tel");
            //获取文件长度
            int lenght = fin.available();
            byte[] buffer = new byte[lenght];
            fin.read(buffer);
            //将byte数组转换成指定格式的字符串
            result=new String(buffer);

        } catch (Exception e) {
            e.printStackTrace();
        }
        tel.setText(result);
    }

    private void inituser() {
        TextView now=(TextView)findViewById(R.id.grxx_wo);
        now.setText(" "+usepath);
    }

    private void initnicheng() {
        nicheng=(TextView)findViewById(R.id.nicheng);
        String result="";
        try {
            FileInputStream fin = openFileInput(usepath+"nicheng");
            //获取文件长度
            int lenght = fin.available();
            byte[] buffer = new byte[lenght];
            fin.read(buffer);
            //将byte数组转换成指定格式的字符串
            result=new String(buffer);

        } catch (Exception e) {
            e.printStackTrace();
        }
        nicheng.setText(result);
    }

    private void initListener() {

        iv_photo.setOnClickListener(this);
    }

    private void initView() {
        setContentView(R.layout.activity_gerenxx);
        iv_photo = (ImageView) findViewById(R.id.iv_photo);
        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");// 从SD卡中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            iv_photo.setImageDrawable(drawable);
        } else {
            /**
             * 如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_photo:// 更换头像
                showTypeDialog();
                break;
        }
    }

    private void showTypeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                startActivityForResult(intent2, 2);// 采用ForResult打开
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);// 保存在SD卡中
                        iv_photo.setImageBitmap(head);// 用ImageView显示出来
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public AlertDialog.Builder createLoadingDialog(Context context, String msg) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view

        numberpicker2 = (NumberPicker) v.findViewById(R.id.numberpicker2);
        numberpicker2.setMaxValue(12);
        numberpicker2.setMinValue(1);
        numberpicker2.setValue(month);
        numberpicker2.setFocusable(true);
        numberpicker2.setFocusableInTouchMode(true);
        numberpicker2.setOnValueChangedListener(monthChangedListener);

        /*
         * / setMaxValue根据每月的天数不一样，使用switch()进行分别判断
         */
        numberpicker3 = (NumberPicker) v.findViewById(R.id.numberpicker3);
        numberpicker3.setMinValue(1);
        numberpicker3.setMaxValue(31);
        numberpicker3.setValue(day);
        numberpicker3.setFocusable(true);
        numberpicker3.setFocusableInTouchMode(true);
        numberpicker3.setOnValueChangedListener(dayChangedListener);
        text1 = (TextView) v.findViewById(R.id.textxing);

        AlertDialog.Builder loadingDialog = new AlertDialog.Builder(context);
        loadingDialog.setMessage("请选择生日");
        loadingDialog.setView(v);
        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {


                        String message = " "+month + "月" + day + "日" +"\t" + text1.getText().toString();
                        tv1.setText(message);
                        if(!message.isEmpty()) {
                            try {
                                FileOutputStream fout = openFileOutput(usepath+"bday", MODE_PRIVATE);//获得FileOutputStream
                                //将要写入的字符串转换为byte数组
                                byte[] bytes = message.getBytes();
                                fout.write(bytes);//将byte数组写入文件
                                fout.close();//关闭文件输出流
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
        loadingDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
        return loadingDialog;

    }



    private NumberPicker.OnValueChangeListener monthChangedListener = new NumberPicker.OnValueChangeListener() {

        @Override
        public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
            // TODO Auto-generated method stub
            month = numberpicker2.getValue();
            xingzuo();
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    numberpicker3.setMaxValue(31);
                    break;
                case 2:
                    numberpicker3.setMaxValue(29);
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    numberpicker3.setMaxValue(30);
                    break;

                default:
                    break;
            }
        }

    };

    private NumberPicker.OnValueChangeListener dayChangedListener = new NumberPicker.OnValueChangeListener() {

        @Override
        public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
            // TODO Auto-generated method stub
            day = numberpicker3.getValue();
            xingzuo();
        }

    };

    void xingzuo() {

        if ((month == 1 && day > 19) || (month == 2 && day < 19)) {
            text1.setText("水瓶座");
        } else if ((month == 2 && day > 18) || (month == 3 && day < 21)) {
            text1.setText("双鱼座");
        } else if ((month == 3 && day > 20) || (month == 4 && day < 20)) {
            text1.setText("白羊座");
        } else if ((month == 4 && day > 19) || (month == 5 && day < 21)) {
            text1.setText("金牛座");
        } else if ((month == 5 && day > 20) || (month == 6 && day < 22)) {
            text1.setText("双子座");
        } else if ((month == 6 && day > 21) || (month == 7 && day < 23)) {
            text1.setText("巨蟹座");
        } else if ((month == 7 && day > 22) || (month == 8 && day < 23)) {
            text1.setText("狮子座");
        } else if ((month == 8 && day > 22) || (month == 9 && day < 23)) {
            text1.setText("处女座");
        } else if ((month == 9 && day > 22) || (month == 10 && day < 24)) {
            text1.setText("天秤座");
        } else if ((month == 10 && day > 23) || (month == 11 && day < 23)) {
            text1.setText("天蝎座");
        } else if ((month == 11 && day > 22) || (month == 12 && day < 22)) {
            text1.setText("射手座");
        } else if ((month == 12 && day > 21) || (month == 1 && day < 20)) {
            text1.setText("摩羯座");
        }

    }

    private void selectAddress(String aa,String bb,String cc) {
        CityConfig cityConfig = new CityConfig.Builder(Activity_gerenxx.this)
                .province(aa)
                .city(bb)
                .district(cc)
                .build();

//配置属性

        CityPickerView cityPicker = new CityPickerView(cityConfig);
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {

            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {


                //ProvinceBean 省份信息
                //CityBean     城市信息
                //DistrictBean 区县信息



                //当wheelType==CityConfig.WheelType.PRO时，CityBean和DistrictBean为null
                //当wheelType==CityConfig.WheelType.PRO_CITY时， DistrictBean为null
                //当wheelType==CityConfig.WheelType.PRO_CITY_DIS时， 可取省市区三个对象的值

                //使用之前需判断province、city、district是否等于null

                try {
                    FileOutputStream fout = openFileOutput(usepath+"pro", MODE_PRIVATE);//获得FileOutputStream
                    //将要写入的字符串转换为byte数组
                    String  message=province.getName();
                    byte[]  bytes = message.getBytes();
                    fout.write(bytes);//将byte数组写入文件
                    fout.close();//关闭文件输出流
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    FileOutputStream fout = openFileOutput(usepath+"city", MODE_PRIVATE);//获得FileOutputStream
                    //将要写入的字符串转换为byte数组
                    String  message=city.getName();
                    byte[]  bytes = message.getBytes();
                    fout.write(bytes);//将byte数组写入文件
                    fout.close();//关闭文件输出流
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    FileOutputStream fout = openFileOutput(usepath+"dis", MODE_PRIVATE);//获得FileOutputStream
                    //将要写入的字符串转换为byte数组
                    String  message=district.getName();
                    byte[]  bytes = message.getBytes();
                    fout.write(bytes);//将byte数组写入文件
                    fout.close();//关闭文件输出流
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(province!=null&city!=null&district!=null)
                    tv2.setText(" "+province.getName() + " - " + city.getName() + " - " + district.getName());
            }
            /*@Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                tv2.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }*/

            @Override
            public void onCancel() {

            }
        });
    }

}
