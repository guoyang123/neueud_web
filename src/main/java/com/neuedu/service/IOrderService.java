package com.neuedu.service;

import com.neuedu.common.ServerResponse;

import java.util.Map;

public interface IOrderService {

    public ServerResponse pay(Long orderNo, Integer userid);

    public  String  alipaycallback(Map<String,String> map);

    public ServerResponse query_order_pay_status(Long orderNo);

    /**
     * 创建订单
     * */
    public ServerResponse createOrder(Integer shippingid,Integer userid);

}
