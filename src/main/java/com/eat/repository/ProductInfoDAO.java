package com.eat.repository;

import com.eat.po.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author yangxuan
 * 商品信息dao
 */
public interface ProductInfoDAO extends JpaRepository<ProductInfo, String> {

    /**
     * 根据 商品状态 查询出 商品信息
     * @param productStatus
     * @return
     */
    public List<ProductInfo> findByProductStatus(Integer productStatus);

}
