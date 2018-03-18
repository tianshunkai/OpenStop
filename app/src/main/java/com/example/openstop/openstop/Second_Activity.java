package com.example.openstop.openstop;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tianshunkai on 2017/11/22.
 */

public class Second_Activity extends AppCompatActivity {
    private  EditText need;
    private EditText editText;
    private GridView gridview;
    private Button sou;
    private int[] imgs = {R.drawable.tianping,R.drawable.ktv,R.drawable.chaoshi,R.drawable.gjcz,
            R.drawable.gongyuan,R.drawable.jianshnegf,R.drawable.jiayouz,R.drawable.jiuba,R.drawable.jiuidan,R.drawable.tingchc,R.drawable.tushug,R.drawable.yiyuan};
    private String[] names= {"餐饮","KTV","超市","公交车站",
            "公园","健身房","加油站","酒吧","酒店","停车场","图书馆","医院"};

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.layout_second);


        editText=(EditText) findViewById(R.id.search_edit_text);
        sou = (Button) findViewById(R.id.doit);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editText.getText().toString().isEmpty())
                    sou.setEnabled(false);
                else
                    sou.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Button xx = (Button) findViewById(R.id.rd1);
        xx.setOnClickListener(new Button.OnClickListener() {//创建监听
            public void onClick(View v) {
                Intent inten = new Intent(Second_Activity.this, sss.class);
                startActivity(inten);

            }
        });

        Button yy = (Button) findViewById(R.id.rd2);
        yy.setOnClickListener(new Button.OnClickListener() {//创建监听
            public void onClick(View v) {
                Intent inten = new Intent(Second_Activity.this, Activity_gerenxx.class);
                startActivity(inten);

            }
        });



        sou.setOnClickListener(new Button.OnClickListener() {//创建监听
            public void onClick(View v) {
                need = (EditText) findViewById(R.id.search_edit_text);
                Intent inten = new Intent(Second_Activity.this, zhoubian.class);
                inten.putExtra("eric", need.getText().toString());
                startActivity(inten);

            }
        });

        gridview = (GridView) findViewById(R.id.gridview);
        MyAdapter adapter = new MyAdapter();
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent inten = new Intent(Second_Activity.this, zhoubian.class);
                inten.putExtra("eric", names[position]);
                startActivity(inten);
            }
        });

    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imgs.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v= View.inflate(Second_Activity.this, R.layout.layout_gridt, null);
            ImageView iv_guide= (ImageView) v.findViewById(R.id.iv_guide);
            TextView tv_guide = (TextView) v.findViewById(R.id.tv_guide);
            iv_guide.setImageResource(imgs[position]);
            tv_guide.setText(names[position]);
            return v;
        }

    }

}
