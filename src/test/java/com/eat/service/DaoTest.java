package com.eat.service;

import com.eat.repository.ProductCategoryDAO;
import com.eat.po.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DaoTest {

    @Autowired
    private ProductCategoryDAO categoryDAO;

    @Test
    public void testFind(){
        List<ProductCategory> cats = categoryDAO.findAll();
        Stream<ProductCategory> stream = cats.stream();
        System.out.println("-------------------------------------------------------------------------------------------------");
        stream.filter((t) -> "小星星".equals(t.getCategoryName()))
                .forEach((cat)->System.out.println(cat.toString()));
        System.out.println("-------------------------------------------------------------------------------------------------");
    }

    public ProductCategory testFindOne(Integer category){
        Optional<ProductCategory> one = categoryDAO.findById(category);
        ProductCategory categoryDto = one.get();

        return categoryDto;
    }

    @Test
    @Transactional    //此处事物  非service事物  只是为了完全回滚 不讲测试数据 插入数据库
    public void testSave(){
        ProductCategory category = new ProductCategory();
        category.setCategoryName("小星星");
        category.setCategoryType(1);
        categoryDAO.save(category);
    }

    @Test
    public void testUpdate(){
        ProductCategory category = testFindOne(2);
        Assert.assertNotNull(category);
        category.setCategoryName("学习");

        categoryDAO.save(category);
    }

    @Test
    public void findListInTest(){
        List<Integer> lists = Arrays.asList(1,2,3,5);
        List<ProductCategory> allById = categoryDAO.findAllById(lists);
        allById.stream().forEach((cat)->System.out.println(cat.toString()));
    }

    @Test
    public void testFindByTypes(){
        List<Integer> types = Arrays.asList(2);
        List<ProductCategory> categories = categoryDAO.findProductCategoriesByCategoryTypeIn(types);
        Stream<ProductCategory> stream = categories.stream();
        stream.forEach((cat)->System.out.println(cat.toString()));
    }

}
