package com.neuedu.dao;

import com.neuedu.pojo.Order;
import com.neuedu.pojo.OrderItem;
import com.neuedu.pojo.PayInfo;

import java.util.List;

public interface IOrderDao {

    /**
     * 查询订单信息
     * @param  orderNo
     * @param  userid
     * */
    public Order findOrderByOrderNoAndUserid(Long orderNo,Integer userid);
    /**
     * 查询订单信息
     * @param  orderNo
     * */
    public Order findOrderByOrderNo(Long orderNo);
    /**
     * 根据订单编号查询订单明细
     * @param  orderNo
     * */
    public List<OrderItem>  findOrderItemsByOrderNo(Long orderNo);

    /**
     * 修改订单支付状态
     * */
    public  Integer  updateOrderStatusByOrderNo(Integer status,Long orderno);


    /**
     * 添加支付信息
     * */
    public  Integer  addPayInfo(PayInfo payInfo);


    /**
     * 添加订单接口
     *
     * */
    public  Integer addOrder(Order order);

    /**
     * 批量插入订单明细
     *
     * */
    public  Integer  batchInsertOrderItem(List<OrderItem> orderItemList);

}
