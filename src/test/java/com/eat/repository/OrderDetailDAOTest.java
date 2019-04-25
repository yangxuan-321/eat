package com.eat.repository;

import com.eat.po.OrderMaster;
import com.eat.enums.OrderStatusEnum;
import com.eat.enums.PayStatusEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * @author : yangxuan
 * @description :
 * @Time : Created in 0:09 2018/6/2
 * @Modifyed By :
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailDAOTest {

    @Autowired
    private OrderMasterDAO orderMasterDAO;

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123457");
        orderMaster.setBuyerName("杨璇");
        orderMaster.setBuyerPhone("18772963897");
        orderMaster.setBuyerAddress("周家嘴路");
        orderMaster.setBuyerOpenid("197615");
        orderMaster.setOrderAmount(new BigDecimal(123.56));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW);
        orderMaster.setPayStatus(PayStatusEnum.SUCCESS);

        orderMasterDAO.save(orderMaster);
    }

    @Test
    public void findByOrderId() {
        Pageable pageable = new PageRequest(0, 1);
        Page<OrderMaster> byBuyerOpenid = orderMasterDAO.findByBuyerOpenid("197615", pageable);
    }
}