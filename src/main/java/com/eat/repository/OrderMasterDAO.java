package com.eat.repository;

import com.eat.po.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : yangxuan
 * @description :
 * @Time : Created in 23:59 2018/6/1
 * @Modifyed By :
 */
public interface OrderMasterDAO extends JpaRepository<OrderMaster, String> {

    /**
     * 通过买家的 openid来查找 会有一个分页
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    public Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);

}
