package com.eat.form;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author : yangxuan
 * @description :
 * @Time : Created in 23:11 2018/6/5
 * @Modifyed By :
 */
public class CreateOrderForm implements Serializable {
    @NotEmpty(message = "下单用户名称不能为空")
    private String name;

    @NotEmpty(message = "下单用户手机不能为空")
    private String phone;

    @NotEmpty(message = "下单用户地址不能为空")
    private String address;

    @NotEmpty(message = "下单用户openid不能为空")
    private String openid;

    @NotEmpty(message = "购物车不能为空")
    private String items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }
}
