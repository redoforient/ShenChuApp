package com.shenchu.app.json;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.shenchu.app.framework.IDataListener;
import com.shenchu.app.framework.IHttpListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author zhangll
 * @description
 * @date 2020/11/26 14:05
 */
public class JsonHttpListener<T> implements IHttpListener {
    //1、用户用什么样的javabean来接收数据
    private Class<T> response;

    //2、需要把最后的结果以对象的方式交给用户
    private IDataListener<T> mDataListener;

    //3、线程切换
    private Handler handler = new Handler(Looper.getMainLooper());

    public JsonHttpListener(Class<T> response, IDataListener<T> dataListener) {
        this.response = response;
        mDataListener = dataListener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        //inputStream已经有了数据
        //inputStream中的数据转化为String
        String content = getContent(inputStream);
        Log.i("JsonHttpListener","服务端响应报文："+content);
        //按用户的要求转化成对应的javabean
        final T responseObj = JSON.parseObject(content,response);
        //responseObj需要发送给最终用户
        handler.post(new Runnable() {
            @Override
            public void run() {
                mDataListener.onSuccess(responseObj);
            }
        });

    }

    private String getContent(InputStream inputStream) {
        String result = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int len = -1;
            while ((len = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            result = baos.toString();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    public void onFailure() {

    }
}
