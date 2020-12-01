package com.shenchu.app.json;

import com.shenchu.app.framework.IHttpListener;
import com.shenchu.app.framework.IHttpRequest;

import java.io.OutputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStream;

/**
 * @author zhangll
 * @description
 * @date 2020/11/26 10:54
 */
public class JsonHttpRequest implements IHttpRequest {
    private String url;
    private byte[] params;
    private IHttpListener mHttpListener;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setParams(byte[] params) {
        this.params = params;
    }

    /**
     * 真实的网络操作在这里执行
     * urlconnection socket okhtp
     * 只要能支持到inputStream的方式都能用
     */
    @Override
    public void execute() {
        //执行网络操作
        HttpURLConnection connection = null;
        try {
            //创建URL对象
            URL url = new URL(this.url);
            //打开连接，得到HttpURLConnection对象
            connection = (HttpURLConnection) url.openConnection();
            //设置请求方式，连接超时，读取数据超时
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(6000);
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            //链接服务器
            connection.connect();
            OutputStream out = connection.getOutputStream();
            out.write(params);
            out.close();
            //发送请求，得到响应数据
            int responseCode = connection.getResponseCode();
            //必须是200才读
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //得到InputStream，读取成string
                InputStream is = connection.getInputStream();
                mHttpListener.onSuccess(is);
                is.close();
            } else {
                mHttpListener.onFailure();
                throw new RuntimeException("请求失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("请求失败");
        } finally {
            //关闭httpURLConnection对象
            connection.disconnect();
        }

    }

    //两个接口需要连接在一起
    @Override
    public void setListener(IHttpListener listener) {
        this.mHttpListener = listener;
    }
}
