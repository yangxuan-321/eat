package com.eat.exception.enums;

/**
 * @author : yangxuan
 * @description : controller层 exception的枚举
 * @Time : Created in 23:45 2018/6/5
 * @Modifyed By :
 */
public enum ResultExceptionEnum {
    PARAM_ERROR("13001", "参数错误"),
    CART_EMPTY("13002", "购物车为空");

    private String code;

    private String message;

    ResultExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
