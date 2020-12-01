package com.shenchu.app.bean;

/**
 * @author zhangll
 * @description
 * @date 2020/11/26 15:23
 */
public class ResponseBean {
    private int error_code;
    private String resultcode;
    private String reason;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
