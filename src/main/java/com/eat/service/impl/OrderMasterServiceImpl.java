package com.eat.service.impl;

import com.eat.convert.OrderMaster2OrderMasterDTO;
import com.eat.dto.CartDTO;
import com.eat.dto.OrderMasterDTO;
import com.eat.enums.OrderStatusEnum;
import com.eat.enums.PayStatusEnum;
import com.eat.exception.AppException;
import com.eat.exception.enums.OrderExceptionEnum;
import com.eat.po.OrderDetail;
import com.eat.po.OrderMaster;
import com.eat.po.ProductInfo;
import com.eat.repository.OrderDetailDAO;
import com.eat.repository.OrderMasterDAO;
import com.eat.service.OrderMasterService;
import com.eat.service.ProductService;
import com.eat.util.KeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : yangxuan
 * @description :
 * @Time : Created in 11:21 2018/6/2
 * @Modifyed By :
 */
@Service("com.eat.service.impl.OrderMasterServiceImpl")
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private OrderMasterDAO masterDAO;

    @Autowired
    public OrderDetailDAO detailDAO;

    @Autowired
    public  ProductService productService;

    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(OrderMasterServiceImpl.class);

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderMasterDTO create(OrderMasterDTO masterDTO) {
        List<OrderDetail> orderDetails = masterDTO.getOrderDetails();

        //BigDecimal orderAmount = new BigDecimal(0);
        BigDecimal orderAmount = null;

        String orderId = KeyUtil.getUniqueKey();

        //1.查询商品数量价格
        for (OrderDetail orderDetail: orderDetails) {
            //查询商品是否存在
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            //商品不存在
            if(null == productInfo){
                throw new AppException(OrderExceptionEnum.PRODUCT_NOT_EXISTS.getCode(), OrderExceptionEnum.PRODUCT_NOT_EXISTS.getMessage());
            }

            //2.计算订单总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()));

            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setOrderId(orderId);
        }
        //写入订单数据库
        detailDAO.saveAll(orderDetails);

        OrderMaster orderMaster = new OrderMaster();
        masterDTO.setOrderId(orderId);
        BeanUtils.copyProperties(masterDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW);
        orderMaster.setPayStatus(PayStatusEnum.WAIT);
        masterDAO.save(orderMaster);

        //3.扣库存
        List<CartDTO> carts = orderDetails.stream().
                map(orderDetail -> new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity())).
                collect(Collectors.toList());

        productService.decreaseStock(carts);

        return masterDTO;
    }

    @Override
    public OrderMasterDTO findOne(String orderId) {
        OrderMaster master = masterDAO.getOne(orderId);
        if(null == master){
            throw new AppException(OrderExceptionEnum.ORDER_NOT_EXISTS.getCode(), OrderExceptionEnum.ORDER_NOT_EXISTS.getMessage());
        }

        List<OrderDetail> orderDetails = detailDAO.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetails)){
            throw new AppException(OrderExceptionEnum.ORDER_DETAIL_NOT_EXISTS.getCode(), OrderExceptionEnum.ORDER_DETAIL_NOT_EXISTS.getMessage());
        }

        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        BeanUtils.copyProperties(master, orderMasterDTO);
        orderMasterDTO.setOrderDetails(orderDetails);

        return orderMasterDTO;
    }

    @Override
    public Page<OrderMasterDTO> findList(String buyerOpenId, Pageable pageable) {
        Page<OrderMaster> orderMasters = masterDAO.findByBuyerOpenid(buyerOpenId, pageable);
        List<OrderMaster> content = orderMasters.getContent();
        List<OrderMasterDTO> convert = OrderMaster2OrderMasterDTO.convert(content);
        Page<OrderMasterDTO> masterDTO = new PageImpl<OrderMasterDTO>(convert, pageable, convert.size());

        return masterDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderMasterDTO finish(OrderMasterDTO orderMasterDTO) {
        if(null == orderMasterDTO){
            return null;
        }

        //1.判断订单状态
        if(!OrderStatusEnum.NEW.equals(orderMasterDTO.getOrderStatus())){
            log.error("完成订单操作时，定单状态不符，当前订单号以及状态为:{},{}", orderMasterDTO.getOrderId(), orderMasterDTO.getOrderStatus().getMessage());
            throw new AppException(OrderExceptionEnum.ORDER_STATUS_ERROR.getCode(), OrderExceptionEnum.ORDER_STATUS_ERROR.getMessage());
        }

        OrderMaster orderMaster = new OrderMaster();
        orderMasterDTO.setOrderStatus(OrderStatusEnum.FINISHED);
        BeanUtils.copyProperties(orderMasterDTO, orderMaster);

        //2.修改订单状态
        OrderMaster finish = masterDAO.save(orderMaster);
        if (null == finish || !OrderStatusEnum.FINISHED.equals(finish.getOrderStatus())){
            log.error("完成订单时，更新订单状态失败，当前订单号为:{}", orderMaster.getOrderId());
            throw new AppException(OrderExceptionEnum.ORDER_UPDATE_FAIL.getCode(), OrderExceptionEnum.ORDER_UPDATE_FAIL.getMessage());
        }

        return orderMasterDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderMasterDTO cancel(OrderMasterDTO orderMasterDTO) {

        OrderMaster orderMaster = new OrderMaster();
        //1.判断订单
        if (!orderMasterDTO.getOrderStatus().equals(OrderStatusEnum.NEW)){
            log.error("------------订单状态不为新增状态，订单编号为:{}，订单状态为:{}-------------", orderMasterDTO.getOrderId(), orderMasterDTO.getOrderStatus());
            throw new AppException(OrderExceptionEnum.ORDER_STATUS_ERROR.getCode(), OrderExceptionEnum.ORDER_STATUS_ERROR.getMessage());
        }

        //2.修改订单状态
        OrderMaster cancel = masterDAO.save(orderMaster);
        if(cancel == null || !OrderStatusEnum.CANCEL.equals(cancel.getOrderStatus())){
            log.error("------------订单更新失败订单编号为{}-------------", orderMaster.getOrderId());
            throw new AppException(OrderExceptionEnum.ORDER_UPDATE_FAIL.getCode(), OrderExceptionEnum.ORDER_UPDATE_FAIL.getMessage());
        }
        orderMasterDTO.setOrderStatus(OrderStatusEnum.CANCEL);
        BeanUtils.copyProperties(orderMasterDTO, orderMaster);

        //3.将相关商品返回库存
        List<OrderDetail> orderDetails = orderMasterDTO.getOrderDetails();
        if(CollectionUtils.isEmpty(orderDetails)){
            log.error("------------订单详情为空，订单编号为:{}-------------", orderMaster.getOrderId());
            throw new AppException(OrderExceptionEnum.ORDER_DETAIL_EMPTY.getCode(), OrderExceptionEnum.ORDER_DETAIL_EMPTY.getMessage());
        }
        List<CartDTO> cartDtos = orderDetails.stream().map(orderDetail -> new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity())).collect(Collectors.toList());
        productService.increaseStock(cartDtos);

        //4.如果已支付需要退款
        if ( PayStatusEnum.SUCCESS.equals(orderMaster.getPayStatus())){
            //TODO
        }

        return orderMasterDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderMasterDTO paid(OrderMasterDTO orderMasterDTO) {

        //1.判断订单完成状态
        if(!OrderStatusEnum.NEW.equals(orderMasterDTO.getOrderStatus())){
            log.error("定单不为新建状态，订单号及订单状态为:{},{}", orderMasterDTO.getOrderId(), orderMasterDTO.getOrderStatus().getMessage());
            throw new AppException(OrderExceptionEnum.ORDER_STATUS_ERROR.getCode(), OrderExceptionEnum.ORDER_STATUS_ERROR.getMessage());
        }

        //2.判断支付完成状态
        if(!PayStatusEnum.WAIT.equals(orderMasterDTO.getPayStatus())){
            log.error("支付状态不为待支付状态，订单号及支付状态为:{},{}", orderMasterDTO.getOrderId(), orderMasterDTO.getOrderStatus().getMessage());
            throw new AppException(OrderExceptionEnum.PAY_STATUS_ERROR.getCode(), OrderExceptionEnum.PAY_STATUS_ERROR.getMessage());
        }

        //3.修改支付状态
        OrderMaster orderMaster = new OrderMaster();
        orderMasterDTO.setPayStatus(PayStatusEnum.SUCCESS);
        BeanUtils.copyProperties(orderMasterDTO, orderMaster);

        //4.完成支付
        //TODO

        return orderMasterDTO;
    }
}
