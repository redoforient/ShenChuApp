package com.shenchu.app.framework;

import com.alibaba.fastjson.JSON;

/**
 * @author zhangll
 * @description
 * @date 2020/11/26 10:40
 */
public class HttpTask<T> implements Runnable {

    private IHttpRequest mHttpRequest;
    private IHttpListener mHttpListener;

    public HttpTask(String url, T requestData,
                    IHttpRequest httpRequest,
                    IHttpListener httpListener) {
        this.mHttpRequest = httpRequest;
        this.mHttpListener = httpListener;
        this.mHttpRequest.setUrl(url);
        this.mHttpRequest.setListener(httpListener);
        if (requestData != null) {
            //将请求对象转化成对应格式的字符串
            String dataStr = JSON.toJSONString(requestData);
            try {
                this.mHttpRequest.setParams(dataStr.getBytes("UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void run() {
        this.mHttpRequest.execute();
    }
}
