package com.eat.service.impl;

import com.eat.dto.OrderMasterDTO;
import com.eat.enums.OrderStatusEnum;
import com.eat.exception.AppException;
import com.eat.exception.enums.OrderExceptionEnum;
import com.eat.exception.enums.ResultExceptionEnum;
import com.eat.service.BuyerService;
import com.eat.service.OrderMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : yangxuan
 * @description :
 * @Time : Created in 23:59 2018/6/20
 * @Modifyed By :
 */
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderMasterService orderMasterService;

    private Logger log = LoggerFactory.getLogger(BuyerServiceImpl.class);

    @Override
    public OrderMasterDTO findOrderOne(String openid, String orderId) {
        return checkOrerOwner(openid, orderId);
    }

    @Override
    public OrderMasterDTO cancelOrder(String openid, String orderId) {
        OrderMasterDTO orderMasterDTO = checkOrerOwner(openid, orderId);
        if (null == orderMasterDTO){
            log.error("订单号对应的订单不存在");
            throw new AppException(OrderExceptionEnum.ORDER_NOT_EXISTS.getCode(), OrderExceptionEnum.ORDER_NOT_EXISTS.getMessage());
        }

        OrderMasterDTO cancelOrder = orderMasterService.cancel(orderMasterDTO);
        return cancelOrder;
    }

    private OrderMasterDTO checkOrerOwner(String openid, String orderId){
        OrderMasterDTO orderMasterDTO = orderMasterService.findOne(orderId);
        if (null == orderMasterDTO){
            return null;
        }

        if (!openid.equalsIgnoreCase(orderMasterDTO.getBuyerOpenid())){
            log.error("传入的openid和order对应的订单的openid不同, openid={}, orderId对应的openid={}", openid, orderMasterDTO.getBuyerOpenid());
            throw new AppException(OrderExceptionEnum.ORDER_OWNER_ERROR.getCode(), OrderExceptionEnum.ORDER_OWNER_ERROR.getMessage());
        }

        return orderMasterDTO;
    }

}
