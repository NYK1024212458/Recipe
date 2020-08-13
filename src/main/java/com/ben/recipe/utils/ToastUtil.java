package com.ben.recipe.utils;

import android.content.Context;
import android.widget.Toast;


public class ToastUtil {

    public static void toastShow(Context context, String str){
        Toast.makeText(context,str, Toast.LENGTH_SHORT).show();
    }
}
