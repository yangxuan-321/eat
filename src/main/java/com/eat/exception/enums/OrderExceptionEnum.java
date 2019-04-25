package com.eat.exception.enums;

/**
 * @author : yangxuan
 * @description : 订单异常相关枚举
 * @Time : Created in 12:51 2018/6/2
 * @Modifyed By :
 */
public enum OrderExceptionEnum {
    /**
     * 相关异常枚举
     */
    PRODUCT_NOT_EXISTS("10001", "商品不存在"),
    PRODUCT_STOCK_ERROR("10002", "库存不正确"),

    ORDER_NOT_EXISTS("11001", "订单不存在"),
    ORDER_DETAIL_NOT_EXISTS("11002", "订单详情不存在"),
    ORDER_STATUS_ERROR("11003", "订单状态不符"),
    ORDER_UPDATE_FAIL("11004", "订单更新失败"),
    ORDER_DETAIL_EMPTY("11005", "订单中的订单详情列表为空"),
    ORDER_OWNER_ERROR("11006", "订单不属于当前用户"),

    PAY_STATUS_ERROR("12001", "订单支付状态不符");

    private String code;

    private String message;

    OrderExceptionEnum(String code, String message) {
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
