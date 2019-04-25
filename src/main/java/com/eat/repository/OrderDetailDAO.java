package com.eat.repository;

import com.eat.po.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author : yangxuan
 * @description :
 * @Time : Created in 0:06 2018/6/2
 * @Modifyed By :
 */
public interface OrderDetailDAO extends JpaRepository<OrderDetail, String> {

    /**
     * 通过订单id 查询出订单详情。可能一个订单对应多个订单详情
     * @param orderId
     * @return
     */
    public List<OrderDetail> findByOrderId(String orderId);

}
