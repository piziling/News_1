package com.haobi.news_1.util;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 15739 on 2019/7/15.
 */

public class  HttpUtil {
    static HttpUtil util;
    static OkHttpClient client;


    private HttpUtil(){
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时
                .writeTimeout(10, TimeUnit.SECONDS)//设置读取超时
                .readTimeout(30, TimeUnit.SECONDS)//设置写入超时
                .build();
    }

    //单例的方法
    public static HttpUtil getInstance(){
        if(util==null){
            synchronized (HttpUtil.class){
                if(util==null){
                    util = new HttpUtil();
                }
            }
        }
        return  util;
    }

    public void getDate(String url, final HttpRespon respon){
        Request request = new Request.Builder()
                .url(url)
                .build();

        //开启一个异步请求
        client.newCall(request).enqueue(new Callback() {

            //请求失败
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                respon.onError("连接服务器失败");
                Log.i("测试-网络请求1", "请求服务器失败！ ");
            }
            //有响应
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    //请求失败
                    respon.onError("连接服务器失败");
                    Log.i("测试-网络请求2", "服务器响应失败！ ");
                }
                //获取到接口的数据
                String date = response.body().string();
                respon.parse(date);
            }
        });
    }
}
