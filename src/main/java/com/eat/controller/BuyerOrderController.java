package com.eat.controller;

import com.eat.contants.PageContants;
import com.eat.convert.OrderForm2OrderMasterDTOConvert;
import com.eat.dto.OrderMasterDTO;
import com.eat.exception.AppException;
import com.eat.exception.enums.ResultExceptionEnum;
import com.eat.form.CreateOrderForm;
import com.eat.service.BuyerService;
import com.eat.service.OrderMasterService;
import com.eat.vo.ResultUtil;
import com.eat.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : yangxuan
 * @description : 买家订单controller
 * @Time : Created in 22:53 2018/6/5
 * @Modifyed By :
 */
@Controller
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private BuyerService buyerService;

    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(BuyerOrderController.class);

    /**
     * 创建订单
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO<OrderMasterDTO> createOrder(@Valid CreateOrderForm form, BindingResult bindingResult){

        //1.判断参数是否发生错误
        if(bindingResult.hasErrors()){
            log.error("创建订单参数有误：{}", form);
            throw new AppException(ResultExceptionEnum.PARAM_ERROR.getCode(), ResultExceptionEnum.PARAM_ERROR.getMessage());
        }

        //2.组装参数
        OrderMasterDTO orderMasterDTO = OrderForm2OrderMasterDTOConvert.convert(form);
        if (CollectionUtils.isEmpty(orderMasterDTO.getOrderDetails())){
            log.error("创建订单,购物车不能为空");
            throw new AppException(ResultExceptionEnum.CART_EMPTY.getCode(), ResultExceptionEnum.CART_EMPTY.getMessage());
        }

        //3.创建订单
        OrderMasterDTO result = orderMasterService.create(orderMasterDTO);

        //4.处理返回结果
        Map<String, String> map = new HashMap<String, String>();
        map.put("orderId", result.getOrderId());

        return ResultUtil.success(map);
    }

    /**
     * 订单列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultVO<List<OrderMasterDTO>> list(@RequestParam("openid") String openid,
                                              @RequestParam(value = "page", defaultValue = "0") Integer page,
                                              @RequestParam(value = "size", defaultValue = "10") Integer size){
        if (StringUtils.isEmpty(openid)){
            log.error("openid不能为空");
            throw new AppException(ResultExceptionEnum.PARAM_ERROR.getCode(), ResultExceptionEnum.PARAM_ERROR.getMessage()+",openid为空");
        }

        Pageable pageable = new PageRequest(page, size);
        Page<OrderMasterDTO> list = orderMasterService.findList(openid, pageable);

        return ResultUtil.success(list.getContent());
    }


    /**
     * 订单详情
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public ResultVO<OrderMasterDTO> detail(@RequestParam("openid") String openid,
                                           @RequestParam("orderId") String orderId){
        if (StringUtils.isEmpty(openid)){
            log.error("openid不能为空");
            throw new AppException(ResultExceptionEnum.PARAM_ERROR.getCode(), ResultExceptionEnum.PARAM_ERROR.getMessage()+",openid为空");
        }

        if (StringUtils.isEmpty(orderId)){
            log.error("orderId不能为空");
            throw new AppException(ResultExceptionEnum.PARAM_ERROR.getCode(), ResultExceptionEnum.PARAM_ERROR.getMessage()+",orderId为空");
        }

        OrderMasterDTO orderOne = buyerService.findOrderOne(openid, orderId);

        return ResultUtil.success(orderOne);
    }


    /**
     * 取消订单
     */
    @RequestMapping(value = "/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){
        if (StringUtils.isEmpty(openid)){
            log.error("openid不能为空");
            throw new AppException(ResultExceptionEnum.PARAM_ERROR.getCode(), ResultExceptionEnum.PARAM_ERROR.getMessage()+",openid为空");
        }

        if (StringUtils.isEmpty(orderId)){
            log.error("orderId不能为空");
            throw new AppException(ResultExceptionEnum.PARAM_ERROR.getCode(), ResultExceptionEnum.PARAM_ERROR.getMessage()+",orderId为空");
        }

        final OrderMasterDTO cancel = buyerService.cancelOrder(openid, orderId);

        return ResultUtil.success(null);
    }
}
