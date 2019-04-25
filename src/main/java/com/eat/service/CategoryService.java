package com.eat.service;

import com.eat.po.ProductCategory;

import java.util.List;

/**
 * @author : yangxuan
 * @desc : 类目service
 */
public interface CategoryService {

    /**
     * 通过id查找
     * @param categoryId
     * @return
     */
    public ProductCategory findOne(Integer categoryId);

    /**
     * 查询所有
     * @return
     */
    public List<ProductCategory> findAll();

    /**
     * 通过类型查找
     * @param types
     * @return
     */
    public List<ProductCategory> findProductCategoriesByCategoryTypeIn(List<Integer> types);

    /**
     * 保存
     * @param category
     * @return
     */
    public ProductCategory save(ProductCategory category);
}
