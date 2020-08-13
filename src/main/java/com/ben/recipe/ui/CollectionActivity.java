package com.ben.recipe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.ben.recipe.R;
import com.ben.recipe.adpter.CookAdapter;
import com.ben.recipe.db.CookDB;
import com.ben.recipe.model.CookBean;
import com.ben.recipe.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *  收藏页面
 */

public class CollectionActivity extends Activity {
    @BindView(R.id.back)
    ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    public static final int SUCCESS = 1;
    public static final int ERROR = 0;

    ListView shoucan_lv;

    CookDB cookDB = null;

    List<CookBean> cookBeans;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SUCCESS) {
                shoucan_lv.setAdapter(new CookAdapter(CollectionActivity.this, cookBeans));
            } else {
                ToastUtil.toastShow(CollectionActivity.this, "没有数据");
            }
        }
    };


    public void initView() {
        shoucan_lv = this.findViewById(R.id.shoucan_lv);
        shoucan_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CollectionActivity.this, CookActivity.class);
                intent.putExtra("cookbean", cookBeans.get(position));
                startActivity(intent);
            }
        });
    }


    /**
     *  获取收藏数据库数据
     */

    public void initData() {
        if (cookDB == null) {
            cookDB = new CookDB(CollectionActivity.this, "t_cook");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                cookBeans = cookDB.findAllData();
                if (cookBeans != null) {
                    Message message = new Message();
                    message.what = SUCCESS;
                    handler.sendMessage(message);
                } else {
                    Message message = new Message();
                    message.what = ERROR;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
