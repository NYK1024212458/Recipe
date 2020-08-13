package com.ben.recipe.biz;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 数据库交互
 */

public class OkHttpTools {
    //创建OkHttpClient对象
    private final OkHttpClient client = new OkHttpClient();

    /**
     * 登录验证的方法
     */
    public String Login(String userphone,String password){
        //创建请求体并传递参数
        RequestBody formBody = new FormBody.Builder()
                .add("USERPHONE",userphone)
                .add("PASSWORD",password)
                .build();
        //创建Request对象
        Request request = new Request.Builder()
                .url("http://47.101.180.197:8080/ordermanagement/Login")
                //.url("http://192.168.2.150:8080/ordermanagement/Login")
                .post(formBody)
                .build();
        //获取Response对象
        try (Response response = client.newCall(request).execute()) {
            //响应成功并返回响应内容
            if (response.isSuccessful()) {
                return response.body().string();
                //此时代码执行在子线程，修改UI的操作使用handler跳转到UI线程
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //响应失败返回" "
        return " ";
    }

    //注册验证的方法
    public String Register(String userphone,String password, String username){
        RequestBody formBody = new FormBody.Builder()
                .add("USERPHONE",userphone)
                .add("PASSWORD",password)
                .add("USERNAME",username)
                .build();
        Request request = new Request.Builder()
                .url("http://47.101.180.197:8080/ordermanagement/Register")
                //.url("http://192.168.2.150:8080/ordermanagement/Register")
                .post(formBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return " ";
    }



}