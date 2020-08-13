package com.ben.recipe.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ben.recipe.R;
import com.ben.recipe.utils.CustomizeVideo;

/**
 * 首次启动轮播背景
 */

public class WelcomeFragment extends Fragment {
    //构造出一个自定义控件
    private CustomizeVideo mVideo;
    public WelcomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mVideo = new CustomizeVideo(getContext());
        int index = getArguments().getInt("index");
        Uri uri;
        uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.intro);

        /*if(index == 1){ //这里的地址是指向本地的视频资源
            uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.intro);
        }else if(index ==2){
            uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.intro);
        }else {
            uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.intro);
        }*/
        mVideo.playVideo(uri);//播放
        return mVideo;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mVideo != null){
            mVideo.stopPlayback();
        }
    }
}
