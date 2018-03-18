package com.example.openstop.openstop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileOutputStream;

public class Activity_zuche2 extends AppCompatActivity {


    private EditText editText1;
    private EditText editText2;
    private Button button;
    private String usepath;
    private SharedPreferences sharedPreferences;

    @Override
    public void onBackPressed() {
        //方式一：将此任务转向后台
        //moveTaskToBack(false);

        //方式二：返回手机的主屏幕
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zuche2);

        editText1=(EditText)findViewById(R.id.zc_nicheng);
        editText2=(EditText)findViewById(R.id.zc_dianhua);
        button=(Button)findViewById(R.id.wanc);

        sharedPreferences=getSharedPreferences("mager", MODE_PRIVATE);
        usepath=sharedPreferences.getString("user","");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream fout = openFileOutput(usepath+"nicheng", MODE_PRIVATE);//获得FileOutputStream
                    //将要写入的字符串转换为byte数组
                    String message=editText1.getText().toString();
                    if(message.isEmpty())
                        message="未命名";
                    byte[]  bytes = message.getBytes();
                    fout.write(bytes);//将byte数组写入文件
                    fout.close();//关闭文件输出流
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    FileOutputStream fout = openFileOutput(usepath+"tel", MODE_PRIVATE);//获得FileOutputStream
                    //将要写入的字符串转换为byte数组
                    String message=editText2.getText().toString();
                    if(message.isEmpty())
                        message=" "+"未设置";
                    else
                        message=" "+message;
                    byte[]  bytes = message.getBytes();
                    fout.write(bytes);//将byte数组写入文件
                    fout.close();//关闭文件输出流
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    FileOutputStream fout = openFileOutput(usepath+"bday", MODE_PRIVATE);//获得FileOutputStream
                    //将要写入的字符串转换为byte数组
                    String  message=" "+"未设置";
                    byte[]  bytes = message.getBytes();
                    fout.write(bytes);//将byte数组写入文件
                    fout.close();//关闭文件输出流
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    FileOutputStream fout = openFileOutput(usepath+"pro", MODE_PRIVATE);//获得FileOutputStream
                    //将要写入的字符串转换为byte数组
                    String  message="未设置";
                    byte[]  bytes = message.getBytes();
                    fout.write(bytes);//将byte数组写入文件
                    fout.close();//关闭文件输出流
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    FileOutputStream fout = openFileOutput(usepath+"city", MODE_PRIVATE);//获得FileOutputStream
                    //将要写入的字符串转换为byte数组
                    String  message="未设置";
                    byte[]  bytes = message.getBytes();
                    fout.write(bytes);//将byte数组写入文件
                    fout.close();//关闭文件输出流
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    FileOutputStream fout = openFileOutput(usepath+"dis", MODE_PRIVATE);//获得FileOutputStream
                    //将要写入的字符串转换为byte数组
                    String  message="未设置";
                    byte[]  bytes = message.getBytes();
                    fout.write(bytes);//将byte数组写入文件
                    fout.close();//关闭文件输出流
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent inten = new Intent(Activity_zuche2.this, MainActivity.class);
                startActivity(inten);

            }
        });
    }
}
