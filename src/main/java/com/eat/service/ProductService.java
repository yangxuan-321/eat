package com.eat.service;

import com.eat.dto.CartDTO;
import com.eat.po.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author : yangxuan
 * @description :
 * @Time : Created in 22:35 2018/5/29
 * @Modifyed By :
 */
public interface ProductService {

    /**
     * 根据ID查找
     * @param productId
     * @return
     */
    public ProductInfo findOne(String productId);

    /**
     * 获得所有商家的商品
     * @return
     */
    public List<ProductInfo> findUpAll();

    /**
     * 查找所有
     * @return
     */
    public Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 保存
     * @param productInfo
     * @return
     */
    public ProductInfo save(ProductInfo productInfo);

    /**
     * 加库存
     * @param cartDTOS
     */
    public void increaseStock(List<CartDTO> cartDTOS);

    /**
     * 扣库存
     * @param cartDTOS
     */
    public void decreaseStock(List<CartDTO> cartDTOS);
}
