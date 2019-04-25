package com.eat.service;

import com.eat.dto.OrderMasterDTO;

/**
 * @author : yangxuan
 * @description :
 * @Time : Created in 23:56 2018/6/20
 * @Modifyed By : yangxuan
 */
public interface BuyerService {
    /**
     * 查询一个订单
     * @param openid
     * @param orderId
     * @return
     */
    public OrderMasterDTO findOrderOne(String openid, String orderId);

    /**
     * 取消一个订单
     * @param openid
     * @param orderId
     * @return
     */
    public OrderMasterDTO cancelOrder(String openid, String orderId);
}
