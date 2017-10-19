package com.lazylive.autoResponse.Entity;

/**
 * @author lazy
 * @create 2017/10/12-12:20
 */
public class RequestJson {
    private String userId;
    private String info ;
    private String key;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
