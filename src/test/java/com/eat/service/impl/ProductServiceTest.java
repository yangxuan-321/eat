package com.eat.service.impl;

import com.eat.po.ProductInfo;
import com.eat.enums.ProductStatusEnum;
import com.eat.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author : yangxuan
 * @description :
 * @Time : Created in 22:57 2018/5/29
 * @Modifyed By :
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService service;

    @Test
    public void findOne() {
        ProductInfo one = service.findOne("123");
        Assert.assertEquals("123", one.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> upAll = service.findUpAll();
        Stream<ProductInfo> stream = upAll.stream();
        stream.forEach(productInfo -> System.out.println(productInfo.toString()));
        Assert.assertNotEquals(0, upAll.size());
    }

    @Test
    public void findAll() {
        Pageable page = new PageRequest(0 ,2);
        Page<ProductInfo> all = service.findAll(page);
        List<ProductInfo> content = all.getContent();
        Stream<ProductInfo> stream = content.stream();
        stream.forEach(productInfo -> System.out.println(productInfo.toString()));
    }

    @Test
    public void save() {
        ProductInfo info = new ProductInfo();
        info.setProductId("123");
        info.setCategoryType(2);
        info.setProductName("热干面");
        info.setProductDescription("热干面真好吃");
        info.setProductIcon("http://xxx.jpg");
        info.setProductPrice(new BigDecimal(1.25));
        info.setProductStock(10000);
        info.setProductStatus(ProductStatusEnum.UP.getCode());

        ProductInfo save = service.save(info);
        Assert.assertEquals("123", save.getProductId());
    }
}