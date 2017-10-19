package com.lazylive.autoResponse.Enum;

public enum CommandEnum {
   JOKE("1"),NEWS("2"),CONNOTATION("3");
    private String value = "";

    CommandEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
