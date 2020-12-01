package com.shenchu.app.framework;

/**
 * 给用户的接口
 */
public interface IDataListener<T> {
    void onSuccess(T t);

    void onFailure();
}