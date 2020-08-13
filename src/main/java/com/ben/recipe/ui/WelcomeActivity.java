package com.ben.recipe.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.ben.recipe.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * WelcomeActivity
 */

public class WelcomeActivity extends WelcomeBaseActivity {
    @BindView(R.id.welocome)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        //startAnimation();
        setHandler();
    }

    private void setHandler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences = getSharedPreferences("count", Context.MODE_PRIVATE); // 存在则打开它，否则创建新的Preferences
                int count = preferences.getInt("count", 0); // 取出数据
                /**
                 * 如果用户不是第一次使用则直接调转到显示界面,否则调转到引导界面
                 */
                if (count == 0) {
                    Intent intent1 = new Intent();
                    intent1.setClass(WelcomeActivity.this, WelcomeVideoActivity.class);
                    startActivity(intent1);
                    finish();
                } else {
                    Intent intent2 = new Intent();
                    intent2.setClass(WelcomeActivity.this, SignInActivity.class);
                    startActivity(intent2);
                    finish();
                }
                finish();
                //实例化Editor对象
                SharedPreferences.Editor editor = preferences.edit();
                //存入数据
                editor.putInt("count", 1); // 存入数据
                //提交修改
                editor.commit();
            }
        }, 3000);
    }

    @OnClick(R.id.welocome)
    public void onViewClicked() {
    }
}
