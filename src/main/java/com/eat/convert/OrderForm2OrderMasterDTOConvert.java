package com.eat.convert;

import com.eat.dto.OrderMasterDTO;
import com.eat.exception.AppException;
import com.eat.exception.enums.ResultExceptionEnum;
import com.eat.form.CreateOrderForm;
import com.eat.po.OrderDetail;
import com.eat.po.OrderMaster;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author : yangxuan
 * @description :
 * @Time : Created in 23:49 2018/6/5
 * @Modifyed By :
 */
public class OrderForm2OrderMasterDTOConvert {

    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(OrderForm2OrderMasterDTOConvert.class);

    public static OrderMasterDTO convert(CreateOrderForm form){
        if(null == form){
            return null;
        }

        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        orderMasterDTO.setBuyerName(form.getName());
        orderMasterDTO.setBuyerPhone(form.getPhone());
        orderMasterDTO.setBuyerAddress(form.getAddress());
        orderMasterDTO.setBuyerOpenid(form.getOpenid());

        Gson gson = new Gson();
        List<OrderDetail> orderDetailList = null;
        try {
            orderDetailList = gson.fromJson(form.getItems(), new TypeToken<List<OrderDetail>>(){}.getType());
            orderMasterDTO.setOrderDetails(orderDetailList);
        }catch (Exception e){
            log.error("购物车json转对象失败:{}", e.getMessage());
            throw new AppException(ResultExceptionEnum.PARAM_ERROR.getCode(), ResultExceptionEnum.PARAM_ERROR.getMessage());
        }

        return orderMasterDTO;
    }
}
