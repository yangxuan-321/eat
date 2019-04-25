package com.eat.service.impl;

import com.eat.po.ProductCategory;
import com.eat.repository.ProductCategoryDAO;
import com.eat.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类目service
 *
 * @author : yangxuan
 */
@Service("com.eat.service.impl.CategoryServiceImpl")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryDAO categoryDAO;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return categoryDAO.getOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return categoryDAO.findAll();
    }

    @Override
    public List<ProductCategory> findProductCategoriesByCategoryTypeIn(List<Integer> types) {
        return categoryDAO.findProductCategoriesByCategoryTypeIn(types);
    }

    @Override
    public ProductCategory save(ProductCategory category) {
        return categoryDAO.save(category);
    }
}
