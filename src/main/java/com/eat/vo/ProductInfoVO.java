package com.eat.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * @author : yangxuan
 * @description : 商品详情信息 VO
 * @Time : Created in 23:15 2018/5/31
 * @Modifyed By :
 */
public class ProductInfoVO {

    /** 商品id */
    @JsonProperty("id")
    private String productId;

    /** 商品名称 */
    @JsonProperty("name")
    private String productName;

    /** 商品价格 */
    @JsonProperty("price")
    private BigDecimal productPrice;

    /** 商品描述 */
    @JsonProperty("description")
    private String productDescription;

    /** 商品小图 */
    @JsonProperty("icon")
    private String productIcon;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }

    @Override
    public String toString() {
        return "ProductInfoVO{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productIcon='" + productIcon + '\'' +
                '}';
    }
}
