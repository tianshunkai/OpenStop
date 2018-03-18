package com.example.openstop.openstop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private EditText username;
    private EditText userpassword;
    private MyDBHelper dbHelper;
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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        dbHelper=new MyDBHelper(this);
        //
        boolean isChecked = false;
        CheckBox cbx = (CheckBox) findViewById(R.id.check1);
        cbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //do something
                    Button access = (Button) findViewById(R.id.access1);
                    access.setEnabled(true);
                } else {
                    //do something else
                    Button access = (Button) findViewById(R.id.access1);
                    access.setEnabled(false);
                }
            }
        });
        cbx.setChecked(isChecked);

        Button zhu = (Button) findViewById(R.id.zuche);
        zhu.setOnClickListener(new Button.OnClickListener() {//创建监听
            public void onClick(View v) {
                Intent inten = new Intent(MainActivity.this, zhuce_Activity.class);
                startActivity(inten);

            }
        });
    }

    public void loginClicked(View view) {
        username=(EditText)findViewById(R.id.zhanghao);
        userpassword=(EditText)findViewById(R.id.mima);
        String userName=username.getText().toString();
        String passWord=userpassword.getText().toString();
        if (login(userName,passWord)) {
            sharedPreferences=getSharedPreferences("mager", MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("islogin", true);
            editor.putString("user",userName);
            editor.commit();
            Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
            Intent inten = new Intent(MainActivity.this, Second_Activity.class);
            startActivity(inten);
        }
        else {
            Toast.makeText(MainActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean login(String username,String password){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String sql="select * from user where username=? and password=?";
        Cursor cursor=sdb.rawQuery(sql, new String[]{username,password});
        if(cursor.moveToFirst()==true){
            cursor.close();
            return true;
        }
        return false;
    }


}
