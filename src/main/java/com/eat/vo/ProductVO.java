package com.eat.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author : yangxuan
 * @description : 商品 包含 类目 VO
 * @Time : Created in 23:00 2018/5/31
 * @Modifyed By :
 */
public class ProductVO {

    /** 类目名称[@JsonProperty 是为了转json之后，显示的字段值 。 例如如果不加就是显示 categoryName,加了就是显示JsonProperty] */
    @JsonProperty("name")
    private String categoryName;

    /** 类目类型 */
    @JsonProperty("type")
    private Integer categoryType;

    /** 商品详情list */
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    public List<ProductInfoVO> getProductInfoVOList() {
        return productInfoVOList;
    }

    public void setProductInfoVOList(List<ProductInfoVO> productInfoVOList) {
        this.productInfoVOList = productInfoVOList;
    }

    @Override
    public String toString() {
        return "ProductVO{" +
                "categoryName='" + categoryName + '\'' +
                ", categoryType=" + categoryType +
                ", productInfoVOList=" + productInfoVOList +
                '}';
    }
}
