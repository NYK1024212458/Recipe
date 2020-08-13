package com.ben.recipe.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ben.recipe.R;
import com.ben.recipe.entity.TabEntity;
import com.ben.recipe.ui.fragment.BingzhengFragment;
import com.ben.recipe.ui.fragment.CaidanFragment;
import com.ben.recipe.ui.fragment.ClassIficationFragment;
import com.ben.recipe.ui.fragment.GuanxiFragment;
import com.ben.recipe.ui.fragment.MeFragment;
import com.ben.recipe.ui.fragment.SearchFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 */
public class TabActivity extends AppCompatActivity {

    //private Context mContext = this;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private String[] mTitles = {"菜单", "相生相克", "搜索", "我"};
    private int[] mIconUnselectIds = { R.mipmap.tab_caidan, R.mipmap.tab_guanxi, R.mipmap.tab_sou, R.mipmap.tab_me };
    private int[] mIconSelectIds = { R.mipmap.tab_caidan_on, R.mipmap.tab_guanxi_on, R.mipmap.tab_sou_on, R.mipmap.tab_me_on };

    //tab的标题、选中图标、未选中图标
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private View mDecorView;
    private ViewPager mViewPager;//ViewPager
    private CommonTabLayout mTabLayout;//CommonTabLayout

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        //Tab设置文字和图标
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

/*        mDecorView = getWindow().getDecorView();
        mViewPager = ViewFindUtils.find(mDecorView, R.id.tab_viewpager);
        mViewPager.setAdapter(new TabActivity.MyPagerAdapter(getSupportFragmentManager(), getFragment()));
        mTabLayout = ViewFindUtils.find(mDecorView, R.id.tab_commontablayout);*/
        mDecorView = getWindow().getDecorView();
        mViewPager = findViewById(R.id.tab_viewpager);
        mViewPager.setAdapter(new TabActivity.MyPagerAdapter(getSupportFragmentManager(), getFragment()));
        mTabLayout = findViewById(R.id.tab_commontablayout);
        tabCommontablayout();
    }

    private List<Fragment> getFragment() {
        ArrayList fragments = new ArrayList<>();
        fragments.add(new ClassIficationFragment()); //菜单
        //fragments.add(new CaidanFragment());
        fragments.add(new GuanxiFragment());  //相生相克
        fragments.add(new SearchFragment());  //搜索页面
        fragments.add(new MeFragment());  //我的页面
        return fragments;
    }

    private void tabCommontablayout() {
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            //ViewPage联动
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            //未读消息小红点
            @Override
            public void onTabReselect(int position) {

            }
        });

        //同ViewPager.OnPageChangeListener
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragment) {
            super(fm);
            mFragments = (ArrayList<Fragment>) fragment;
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}
