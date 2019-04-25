package com.eat.service;

import com.eat.dto.OrderMasterDTO;
import com.eat.po.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author : yangxuan
 * @description : 订单主表Service
 * @Time : Created in 1:00 2018/6/2
 * @Modifyed By :
 */
public interface OrderMasterService {

    /** 创建订单 */
    public OrderMasterDTO create(OrderMasterDTO masterDTO);

    /** 查询单个订单--根据订单id */
    public OrderMasterDTO findOne(String orderId);

    /** 查询订单列表--根据买家的openid */
    public Page<OrderMasterDTO> findList(String buyerOpenId, Pageable pageable);

    /** 完结订单 */
    public OrderMasterDTO finish(OrderMasterDTO orderMasterDTO);

    /** 取消订单*/
    public OrderMasterDTO cancel(OrderMasterDTO orderMasterDTO);

    /** 支付订单 */
    public OrderMasterDTO paid(OrderMasterDTO orderMasterDTO);
}
