package com.example.openstop.openstop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_wode extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView1;
    private TextView textView2;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wode);

        Button xx = (Button) findViewById(R.id.t_c);
        xx.setOnClickListener(new Button.OnClickListener() {//创建监听
            public void onClick(View v) {
                sharedPreferences=getSharedPreferences("mager", MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putBoolean("islogin", false);
                editor.commit();
                Intent inten = new Intent(Activity_wode.this, MainActivity.class);
                startActivity(inten);

            }
        });

        Button yy = (Button) findViewById(R.id.grxx);
        yy.setOnClickListener(new Button.OnClickListener() {//创建监听
            public void onClick(View v) {
                Intent inten = new Intent(Activity_wode.this, Activity_gerenxx.class);
                startActivity(inten);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}
