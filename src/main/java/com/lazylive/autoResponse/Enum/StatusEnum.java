package com.lazylive.autoResponse.Enum;

/**
 * Created by LAZY-F on 2017/8/1.
 */
public enum StatusEnum {

    ERROR(4000),SUCCESS(2000);

    private int statusCode;

    StatusEnum(int code){
    this.statusCode=code;
    }

    public int statusCode() {
        return this.statusCode;
    }
}
