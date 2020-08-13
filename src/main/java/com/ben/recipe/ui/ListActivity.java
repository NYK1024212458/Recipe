package com.ben.recipe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ben.recipe.R;
import com.ben.recipe.adpter.CookAdapter;
import com.ben.recipe.config.AppConfig;
import com.ben.recipe.model.CookBean;
import com.ben.recipe.model.Steps;
import com.ben.recipe.utils.ToastUtil;
import com.ben.recipe.utils.WebUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 跳转页
 */
public class ListActivity extends AppCompatActivity {

    public static final int SUCCESS = 1;
    public static final int ERROR = 0;

    TextView details_tv;
    ListView details_lv;

    List<CookBean> cookBeans;

    String name ;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == SUCCESS){
                details_lv.setAdapter(new CookAdapter(ListActivity.this,cookBeans));
            }else if(msg.what == ERROR){
                Log.d(TAG, "handleMessage: 网络错误");
                ToastUtil.toastShow(ListActivity.this,"网络错误");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_list);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        initView();
        initData();
    }

    public void initView(){
        details_tv = findViewById(R.id.details_tv);
        details_lv = findViewById(R.id.details_lv);
        details_tv.setText(name);
        details_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListActivity.this, CookActivity.class);
                intent.putExtra("cookbean",cookBeans.get(position));
                startActivity(intent);
            }
        });
    }

    public void initData(){
        query();
    }
 private  String TAG =  getClass().getSimpleName().toString();
    public void query(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = null;
                try {
                    url = AppConfig.QUERY +"?key="+AppConfig.KEY+"&menu="+ URLEncoder.encode(name,"UTF-8");
                    Log.d(TAG, "run:查询菜名 "+ url);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.i("TAG", url);
                try {
                    String json = WebUtil.getStringByInputStream(WebUtil.getByWeb(url));
                    Log.i("TAG", json);
                    Log.d(TAG, "run:查询菜名最后转化为json "+ json);
                    JSONObject jsonObject = new JSONObject(json);
                    if(jsonObject.getString("resultcode").equals("200")){
                        cookBeans = new ArrayList<>();
                        JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");
                        Log.i("TAG", jsonArray.toString());
                        for(int i = 0 ; i < jsonArray.length() ; i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            CookBean cookBean = new CookBean();
                            cookBean.setId(jsonObject1.getString("id"));
                            cookBean.setTitle(jsonObject1.getString("title"));
                            cookBean.setTags(jsonObject1.getString("tags"));
                            cookBean.setImtro(jsonObject1.getString("imtro"));
                            cookBean.setIngredients(jsonObject1.getString("ingredients"));
                            cookBean.setBurden(jsonObject1.getString("burden"));
                            List<String> albums = new ArrayList<>();
                            JSONArray jsonArray1 = jsonObject1.getJSONArray("albums");
                            for(int j = 0 ; j < jsonArray1.length() ; j++){
                                albums.add(jsonArray1.getString(j));
                            }
                            cookBean.setAlbums(albums);
                            JSONArray jsonArray2 = jsonObject1.getJSONArray("steps");
                            List<Steps> steps = new ArrayList<>();
                            for(int k = 0 ; k < jsonArray2.length() ; k++){
                                JSONObject jsonObject2 = jsonArray2.getJSONObject(k);
                                Steps step = new Steps();
                                step.setImg(jsonObject2.getString("img"));
                                step.setStep(jsonObject2.getString("step"));
                                steps.add(step);
                            }
                            cookBean.setSteps(steps);
                            cookBeans.add(cookBean);
                        }
                        Message message = new Message();
                        message.what = SUCCESS;
                        handler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Message message = new Message();
                    message.what = ERROR;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Message message = new Message();
                    message.what = ERROR;
                    handler.sendMessage(message);
                }
            }
        }).start();

    }

    public void onBack(View view){
        finish();
    }
}
