package com.ben.recipe.ui.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.ben.recipe.R;
import com.ben.recipe.ui.CollectionActivity;
import com.ben.recipe.ui.SignInActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeFragment extends Fragment {

    @BindView(R.id.kaifazhe)
    LinearLayout kaifazhe;
    @BindView(R.id.chanpingsheji)
    LinearLayout chanpingsheji;
    @BindView(R.id.chuping)
    LinearLayout chuping;
    @BindView(R.id.weixin)
    LinearLayout weixin;
    @BindView(R.id.weibo)
    LinearLayout weibo;
    @BindView(R.id.youxiang)
    LinearLayout youxiang;
    @BindView(R.id.shoucang)
    CardView shoucang;
    @BindView(R.id.quit)
    LinearLayout quit;

    private View view;
    private ProgressDialog dialog;
    private SharedPreferences pref;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick({R.id.kaifazhe, R.id.chanpingsheji, R.id.chuping, R.id.weixin, R.id.weibo, R.id.youxiang, R.id.quit, R.id.shoucang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.kaifazhe:
                break;
            case R.id.chanpingsheji:
                break;
            case R.id.chuping:
                break;
            case R.id.weixin:
                break;
            case R.id.weibo:
                break;
            case R.id.youxiang:
                break;
            case R.id.quit:
                //getActivity().onBackPressed();//销毁自己
                dialog = new ProgressDialog(getActivity());
                dialog.setTitle("提示");
                dialog.setMessage("正在退出，请稍后...");
                dialog.setCancelable(false);
                dialog.show();
                //清空数据
                pref = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                Intent intentquit = new Intent(getActivity(), SignInActivity.class);
                startActivity(intentquit);
                dialog.dismiss();
                break;
            case R.id.shoucang:
                Intent intent = new Intent(getActivity(), CollectionActivity.class);
                startActivity(intent);
                break;
        }
    }
}
