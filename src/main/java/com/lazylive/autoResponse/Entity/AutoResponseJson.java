package com.lazylive.autoResponse.Entity;

/**
 * @author lazy
 * @create 2017/9/29-21:22
 */
public class AutoResponseJson {
    private int code;
    private String status;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}