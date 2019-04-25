package com.eat.controller;

import com.eat.po.ProductCategory;
import com.eat.po.ProductInfo;
import com.eat.service.CategoryService;
import com.eat.service.ProductService;
import com.eat.service.impl.ProductServiceImpl;
import com.eat.vo.ProductVO;
import com.eat.vo.ResultUtil;
import com.eat.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : yangxuan
 * @description : 买家商品列表controller
 * @Time : Created in 22:11 2018/5/31
 * @Modifyed By :
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultVO list(){

        //1.查询所有商家商品
        List<ProductInfo> upAll = productService.findUpAll();

        //2.拿到所有商品的类目类型
        Stream<ProductInfo> stream = upAll.stream();
        List<Integer> categoryTypes = stream.map(productInfo -> productInfo.getCategoryType()).collect(Collectors.toList());


        //3.数据拼装
        List<ProductCategory> productCategories = categoryService.findProductCategoriesByCategoryTypeIn(categoryTypes);
        Stream<ProductCategory> stream1 = productCategories.stream();
        List<ProductVO> productVOS = stream1.map(category -> ProductServiceImpl.packageProductVO(category, upAll)).collect(Collectors.toList());

        ResultVO success = ResultUtil.success(productVOS);

        return success;
    }
}
