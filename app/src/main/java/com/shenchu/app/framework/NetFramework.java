package com.shenchu.app.framework;

import com.shenchu.app.json.JsonHttpListener;
import com.shenchu.app.json.JsonHttpRequest;

/**
 * @author zhangll
 * @description
 * @date 2020/11/26 14:11
 */
public class NetFramework<T,M> {

    /**
     * @param url
     * @param requestParams
     * @param reponse
     * @param dataListener
     * @param <T>：请求参数（输入）
     * @param <M>：响应结果（输出）
     */
    public static <T, M> void sendJsonRequest(String url,
                                              T requestParams,
                                              Class<M> reponse,
                                              IDataListener<M> dataListener) {

        IHttpRequest httpRequest = new JsonHttpRequest();
        IHttpListener httpListener = new JsonHttpListener<M>(reponse, dataListener);
        HttpTask httpTask =new HttpTask<T>(url, requestParams, httpRequest, httpListener);
        ThreadManager.getInstance().addTask(httpTask);
    }

    //public static void sendXmlRequest();
    //public static void sendProtobufRequest();
}
