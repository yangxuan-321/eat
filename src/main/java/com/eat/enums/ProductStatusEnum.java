package com.eat.enums;

/**
 * @author : yangxuan
 * @description :
 * @Time : Created in 22:48 2018/5/29
 * @Modifyed By :
 */
public enum  ProductStatusEnum {
    UP(0, "在架"),
    DOWN(1, "下架")
    ;

    /** Code */
    private Integer code;
    /** Message */
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
