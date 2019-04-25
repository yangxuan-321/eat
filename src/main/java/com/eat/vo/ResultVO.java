package com.eat.vo;

/**
 * @author : yangxuan
 * @description : http请求返回的标准对象
 * @Time : Created in 22:20 2018/5/31
 * @Modifyed By :
 */
public class ResultVO<T> {
    /** 错误码 */
    private Integer code;

    /** 提示信息 */
    private String message;

    /** 返回的具体内容 */
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultVO{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
