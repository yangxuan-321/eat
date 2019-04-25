package com.eat.service.impl;

import com.eat.dto.CartDTO;
import com.eat.exception.AppException;
import com.eat.exception.enums.OrderExceptionEnum;
import com.eat.po.ProductCategory;
import com.eat.po.ProductInfo;
import com.eat.enums.ProductStatusEnum;
import com.eat.repository.ProductInfoDAO;
import com.eat.service.ProductService;
import com.eat.vo.ProductInfoVO;
import com.eat.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : yangxuan
 * @description :
 * @Time : Created in 22:43 2018/5/29
 * @Modifyed By :
 */
@Service("com.eat.service.impl.ProductServiceImpl")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoDAO productInfoDAO;

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoDAO.getOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoDAO.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoDAO.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoDAO.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOS) {
        if(CollectionUtils.isEmpty(cartDTOS)){
            return;
        }

        for (CartDTO cartDTO: cartDTOS) {
            String productId = cartDTO.getProductId();
            ProductInfo one = productInfoDAO.getOne(productId);
            if(one != null){
                one.setProductStock(one.getProductStock()+cartDTO.getProductQuantity());
                productInfoDAO.save(one);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decreaseStock(List<CartDTO> cartDTOS) {
        for (CartDTO dto: cartDTOS){
            ProductInfo productInfo = productInfoDAO.getOne(dto.getProductId());
            if(null == productInfo){
                throw new AppException(OrderExceptionEnum.PRODUCT_NOT_EXISTS.getCode(), OrderExceptionEnum.PRODUCT_NOT_EXISTS.getMessage());
            }

            //此处需要加锁
            Integer result = productInfo.getProductStock() - dto.getProductQuantity();
            if (result < 0){
                throw new AppException(OrderExceptionEnum.PRODUCT_STOCK_ERROR.getCode(), OrderExceptionEnum.PRODUCT_STOCK_ERROR.getMessage());
            }

            productInfo.setProductStock(result);
            productInfoDAO.save(productInfo);
        }
    }

    /**
     * 组装商品信息
     * @param category
     * @param productInfos
     * @return
     */
    public static ProductVO packageProductVO(ProductCategory category, List<ProductInfo> productInfos) {
        ProductVO productVO = new ProductVO();
        productVO.setCategoryName(category.getCategoryName());
        productVO.setCategoryType(category.getCategoryType());
        Stream<ProductInfo> stream = productInfos.stream();
        List<ProductInfoVO> collect = stream.
                                        filter(productInfo -> productInfo.getCategoryType().equals(category.getCategoryType())).
                                        map(ProductServiceImpl::packageProductInfoVO).
                                        collect(Collectors.toList());
        productVO.setProductInfoVOList(collect);

        return productVO;
    }


    private static ProductInfoVO packageProductInfoVO(ProductInfo productInfo){
        ProductInfoVO productInfoVO = new ProductInfoVO();
        BeanUtils.copyProperties(productInfo, productInfoVO);

        return  productInfoVO;
    }
}
