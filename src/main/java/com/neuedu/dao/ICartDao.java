package com.neuedu.dao;

import com.neuedu.pojo.Cart;

import java.util.List;

public interface ICartDao {

    /**
     * 根据userid和productid查询购物车中的购物信息
     * */

    public Cart findCartByUseridAndProductid(Integer userid,Integer productid);

    /**
     * 添加购物信息
     *
     * */

    public  Integer addProductToCart(Cart cart);

    /**
     * 更新购物车中商品数量
     * */
    public  Integer updateCartByUseridAndProductid(Cart cart);

    /**
     * 根据userid查询购物集合
     *
     * */
    public List<Cart> findCartsByUserid(Integer userid);

   /**
    * 判断购物车全选
    * @return  0:全选
    *
    * */

   public Integer  isCheckAll(Integer userid);



}
