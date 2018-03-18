package com.example.openstop.openstop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class guid_Activity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private boolean islogin;

    /*/@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_guid);

        //mHandler.sendEmptyMessageAtTime(GOTO_MAIN_ACTIVITY, 5000);//3秒跳转


    }*/

    private final long SPLASH_LENGTH = 3000;
    Handler handler = new Handler();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_guid);
        handler.postDelayed(new Runnable() {  //使用handler的postDelayed实现延时跳转

            public void run() {
                sharedPreferences=getSharedPreferences("mager", MODE_PRIVATE);
                islogin=sharedPreferences.getBoolean("islogin",false);
                if(islogin)
                {
                    Intent inten = new Intent(guid_Activity.this, Second_Activity.class);
                    //Toast.makeText(guid_Activity.this,"111",Toast.LENGTH_SHORT).show();
                    startActivity(inten);
                    finish();
                }
                else
                {
                    Intent inten = new Intent(guid_Activity.this, MainActivity.class);
                    //Toast.makeText(guid_Activity.this,"222",Toast.LENGTH_SHORT).show();
                    startActivity(inten);
                    finish();
                }
            }
        }, SPLASH_LENGTH);//2秒后跳转至应用主界面MainActivity

    }


    /*private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case GOTO_MAIN_ACTIVITY:
                    sharedPreferences=getSharedPreferences("mager", MODE_PRIVATE);
                    islogin=sharedPreferences.getBoolean("islogin",false);
                    if(islogin)
                    {
                        Intent inten = new Intent(guid_Activity.this, Second_Activity.class);
                        Toast.makeText(guid_Activity.this,"111",Toast.LENGTH_SHORT).show();
                        startActivity(inten);
                        finish();
                    }
                    else
                    {
                        Intent inten = new Intent(guid_Activity.this, MainActivity.class);
                        Toast.makeText(guid_Activity.this,"222",Toast.LENGTH_SHORT).show();
                        startActivity(inten);
                        finish();
                    }
                    break;

                default:
                    break;
            }
        };
    };*/
}
