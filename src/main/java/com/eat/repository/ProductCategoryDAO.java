package com.eat.repository;

import com.eat.po.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 商品类目信息表
 * @author : yangxuan
 */
public interface ProductCategoryDAO extends JpaRepository<ProductCategory, Integer> {

    /**
     * 通过类型查找
     * @param types
     * @return
     */
    public List<ProductCategory> findProductCategoriesByCategoryTypeIn(List<Integer> types);

}
