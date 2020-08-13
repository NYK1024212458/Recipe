package com.ben.recipe.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getFragmentLayout(), container , false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        initData();
    }

    public abstract int getFragmentLayout();

    //初始化界面
    public abstract void initView();

    //初始化数据
    public abstract void initData();

}
