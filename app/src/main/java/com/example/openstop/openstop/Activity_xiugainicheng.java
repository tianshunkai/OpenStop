package com.example.openstop.openstop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileOutputStream;

public class Activity_xiugainicheng extends AppCompatActivity {

    private Button back;
    private Button save;
    private EditText editText;

    private String usepath;
    private SharedPreferences sharedPreferences;
    @Override
    public void onBackPressed() {
        //TODO something
        Intent inten = new Intent(Activity_xiugainicheng.this, Activity_gerenxx.class);
        startActivity(inten);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiugainicheng);

        sharedPreferences=getSharedPreferences("mager", MODE_PRIVATE);
        usepath=sharedPreferences.getString("user","");

        editText=(EditText) findViewById(R.id.xiugai_nc);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editText.getText().toString().isEmpty())
                    save.setEnabled(false);
                else
                    save.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        back=(Button) findViewById(R.id.xiugai_nc_back);
        back.setOnClickListener(new Button.OnClickListener() {//创建监听
            public void onClick(View v) {
                Intent inten = new Intent(Activity_xiugainicheng.this, Activity_gerenxx.class);
                startActivity(inten);
                finish();
            }
        });

        save=(Button) findViewById(R.id.xiugai_nc_save);

        save.setOnClickListener(new Button.OnClickListener() {//创建监听
            public void onClick(View v) {
                String message = editText.getText().toString();
                if(!message.isEmpty()) {
                    try {
                        FileOutputStream fout = openFileOutput(usepath+"nicheng", MODE_PRIVATE);//获得FileOutputStream
                        //将要写入的字符串转换为byte数组
                        byte[] bytes = message.getBytes();
                        fout.write(bytes);//将byte数组写入文件
                        fout.close();//关闭文件输出流
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                Intent inten = new Intent(Activity_xiugainicheng.this, Activity_gerenxx.class);
                startActivity(inten);
                finish();

            }
        });
    }
}
