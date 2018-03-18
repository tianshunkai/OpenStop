package com.example.openstop.openstop;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Tianshunkai on 2017/11/22.
 */

public class zhuce_Activity extends AppCompatActivity {
    private MyDBHelper dbHelper;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.layout_zuche);
        dbHelper=new MyDBHelper(this);
    }

    public void logon(View view){
        //SQLiteDatabase db=dbHelper.getWritableDatabase();

        EditText editText3=(EditText)findViewById(R.id.use1);
        EditText editText4=(EditText)findViewById(R.id.pass1);
        EditText editText5=(EditText)findViewById(R.id.passag1);
        String newname =editText3.getText().toString();
        String password=editText4.getText().toString();
        String passwordag=editText5.getText().toString();
        if(passwordag.equals(password)==false)
        {
            Toast.makeText(this,"两次密码输入不相同",Toast.LENGTH_SHORT).show();
        }
        else if (CheckIsDataAlreadyInDBorNot(newname)) {
            Toast.makeText(this,"该账号已被注册，注册失败",Toast.LENGTH_SHORT).show();
        }
        else {

            if (register(newname, password)) {
                sharedPreferences=getSharedPreferences("mager", MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("user",newname);
                editor.commit();
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                Intent inten = new Intent(this, Activity_zuche2.class);
                startActivity(inten);
            }
        }
    }
    //向数据库插入数据
    public boolean register(String a,String b){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String sql="insert into user(username,password) values(?,?)";
        Object obj[]={a,b};
        sdb.execSQL(sql, obj);
        return true;
    }

    public boolean CheckIsDataAlreadyInDBorNot(String value){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String Query = "Select * from user where username =?";
        Cursor cursor = db.rawQuery(Query,new String[] { value });
        if (cursor.getCount()>0){
            cursor.close();
            return  true;
        }
        cursor.close();
        return false;
    }
}
