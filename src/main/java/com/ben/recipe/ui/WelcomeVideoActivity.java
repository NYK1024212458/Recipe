package com.ben.recipe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ben.recipe.R;
import com.ben.recipe.ui.fragment.WelcomeFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首次登录引导页
 */
public class WelcomeVideoActivity extends WelcomeBaseActivity {
    @BindView(R.id.video_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.text)
    TextView textView;
    @BindView(R.id.video_iv1)
    ImageView mpoint1;
    @BindView(R.id.video_iv2)
    ImageView mpoint2;
    @BindView(R.id.video_iv3)
    ImageView mpoint3;
    @BindView(R.id.bt_sign_in)
    Button mButton;

    private ArrayList<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_viedo);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {

        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new MyPagerListener());//viewpager监听
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeVideoActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 初始化数据，添加三个fragment
     */
    private void initData() {

        mFragments = new ArrayList<>();
        WelcomeFragment fragment1 = new WelcomeFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("index", 1);  //指定一个下标，好让滑动是执行下一个fragment
        fragment1.setArguments(bundle1);//注意要将bundel存入，不让在GuideActivity获取下标会出现空指针异常
        mFragments.add(fragment1);

        WelcomeFragment fragment2 = new WelcomeFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("index", 2);  //指定一个下标，好让滑动是执行下一个fragment
        fragment2.setArguments(bundle2);
        mFragments.add(fragment2);

        WelcomeFragment fragment3 = new WelcomeFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("index", 3);  //指定一个下标，好让滑动是执行下一个fragment
        fragment3.setArguments(bundle3);
        mFragments.add(fragment3);
    }

    @OnClick({R.id.video_viewpager, R.id.text, R.id.video_iv1, R.id.video_iv2, R.id.video_iv3, R.id.bt_sign_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.video_viewpager:
                break;
            case R.id.text:
                break;
            case R.id.video_iv1:
                break;
            case R.id.video_iv2:
                break;
            case R.id.video_iv3:
                break;
            case R.id.bt_sign_in:
                break;
        }
    }

    //我们viewpager的适配器
    class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    //设置viewpager的监听，来切换小圆点
    class MyPagerListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            mButton.setVisibility(View.GONE);
            mpoint1.setImageResource(R.mipmap.point);
            mpoint2.setImageResource(R.mipmap.point);
            mpoint3.setImageResource(R.mipmap.point);
            if (position == 0) {
                textView.setText("菜 谱");
                mpoint1.setImageResource(R.mipmap.point_b);
            } else if (position == 1) {
                textView.setText("健 康");
                mpoint2.setImageResource(R.mipmap.point_b);
            } else {
                textView.setText("饮 食");
                mpoint3.setImageResource(R.mipmap.point_b);
                mButton.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

}
