package com.shenchu.app.framework;

public interface IHttpRequest {
    void setUrl(String url);
    void setParams(byte[] params);
    void execute();
    //两个接口需要连接在一起
    void setListener(IHttpListener listener);
}
