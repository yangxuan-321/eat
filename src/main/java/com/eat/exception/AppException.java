package com.eat.exception;

import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * @author : yangxuan
 * @description : App异常
 * @Time : Created in 12:05 2018/6/2
 * @Modifyed By :
 */
public class AppException extends RuntimeException implements Serializable {
    /** 异常原因 */
    private Throwable rootcause;

    /** 异常code */
    private String code = null;

    /** 异常描述 */
    private String message = null;

    public Throwable getRootcause() {
        return rootcause;
    }

    public void setRootcause(Throwable rootcause) {
        this.rootcause = rootcause;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AppException() {
    }

    public AppException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public AppException(Throwable rootcause, String code, String message) {
        this.rootcause = rootcause;
        this.code = code;
        this.message = message;
    }
}
