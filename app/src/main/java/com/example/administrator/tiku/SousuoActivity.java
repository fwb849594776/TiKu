package com.example.administrator.tiku;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

import adapter.QuestionAdapter;
import collector.ActivityCollector;
import http.GetList;
import pojo.Question;

public class SousuoActivity extends AppCompatActivity implements View.OnClickListener{



    private EditText et_search;


    private TextView tv_search_btn;


    private ListView lv_question_sousuo;

    private  List<Question> mohuList;
    Handler handler;

    private GetList getList = new GetList();
    private Toolbar toolbar;
    private QuestionAdapter adapter;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sousuo);
        ActivityCollector.addActivity(this);
        toolbar = (Toolbar) findViewById(R.id.tl_custom);

        toolbar.setTitle("题目查找");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF")); //设置标题颜色
        toolbar.setBackgroundColor(Color.parseColor("#97282F"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_search = (EditText) findViewById(R.id.et_search);
        lv_question_sousuo = (ListView) findViewById(R.id.lv_question_sousuo);
        tv_search_btn = (TextView) findViewById(R.id.tv_search_btn);
        tv_search_btn.setOnClickListener(this);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:{
                        adapter = new QuestionAdapter(mohuList,SousuoActivity.this);
                        lv_question_sousuo.setAdapter(adapter);
                    }
                    break;
                }
            }
        };

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                onBackPressed();
                return  true;
            }
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void onClick(View v) {

        String s = et_search.getText().toString();
        if (s == null){
            s = "";
        }
        mohuList = getList.getMohuList(s);


        Message message = new Message();
        message.what = 1;
        handler.sendMessage(message);

    }
}