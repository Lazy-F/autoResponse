package com.lazylive.autoResponse.Entity;

/**
 * @author lazy
 * @create 2017/9/29-21:22
 */
public class ResponseJson {
    private int code;
    private String status;
    private String text;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String content) {
        this.text = content;
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