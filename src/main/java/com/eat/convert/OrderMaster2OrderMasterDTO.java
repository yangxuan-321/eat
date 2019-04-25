package com.eat.convert;

import com.eat.dto.OrderMasterDTO;
import com.eat.po.OrderMaster;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : yangxuan
 * @description : 将OrderMaster -> OrderMasterDTO
 * @Time : Created in 19:05 2018/6/2
 * @Modifyed By :
 */
public class OrderMaster2OrderMasterDTO {

    /**
     * 将 OrderMaster -> OrderMasterDTO
     * @param master
     * @return
     */
    public static final OrderMasterDTO convert(OrderMaster master){
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        BeanUtils.copyProperties(master, orderMasterDTO);

        return orderMasterDTO;
    }

    /**
     * List操作 OrderMaster -> OrderMasterDTO
     * @param masters
     * @return
     */
    public static final List<OrderMasterDTO> convert(List<OrderMaster> masters){
        if(CollectionUtils.isEmpty(masters)){
            return null;
        }

        List<OrderMasterDTO> collect = masters.stream().map(master -> convert(master)).collect(Collectors.toList());

        return collect;
    }
}
