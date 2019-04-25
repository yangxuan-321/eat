package com.eat.dao;

import com.eat.EatApplicationTests;
import com.eat.po.ProductInfo;
import com.eat.enums.ProductStatusEnum;
import com.eat.repository.ProductInfoDAO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class TestProductInfoDAO extends EatApplicationTests {

    @Autowired
    private ProductInfoDAO productInfoDAO;

//    public ProductInfo testFindOneById(){
//        return productInfoDAO.getOne();
//    }

    @Test
    public void testSave(){
        ProductInfo info = new ProductInfo();
        info.setProductId("125");
        info.setCategoryType(1);
        info.setProductName("小龙虾");
        info.setProductDescription("小龙虾真好吃");
        info.setProductIcon("http://xxx1.jpg");
        info.setProductPrice(new BigDecimal(1.26));
        info.setProductStock(10001);
        info.setProductStatus(ProductStatusEnum.UP.getCode());

        ProductInfo save = productInfoDAO.save(info);
        Assert.assertEquals("125", save.getProductId());
    }

    public ProductInfo testFindOne(){
        String id = "";
        return productInfoDAO.getOne(id);
    }


    public ProductInfo testUpdate(){
        return null;
    }

}



