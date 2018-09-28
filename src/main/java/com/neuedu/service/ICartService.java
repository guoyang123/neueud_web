package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Cart;
import com.neuedu.vo.CartVO;

public interface ICartService {

    /**
     * 添加商品到购物车
     *
     * */

    public ServerResponse<CartVO> addProductToCart(Integer userid,Integer productid,int count);


    /**
     * 查询用户购物信息
     * */
    public  ServerResponse<CartVO> findCartsByUserId(Integer userid);

    /**
     * 更新某个商品的数量
     * */
    public  ServerResponse<CartVO> updateCartByUseridAndProductid(Integer userid,Integer productid,Integer quantity);

/**
 * 删除购物车中的商品
 * */

     public  ServerResponse<CartVO> deleteProductsFromCart(Integer userid,String productIds);


     /**
      * 选中某个商品
      * */
     public   ServerResponse<CartVO>  checkedProductByProductId(Integer userid,Integer productid);
    /**
     * 取消选中某个商品
     * */
    public   ServerResponse<CartVO>  uncheckedProductByProductId(Integer userid,Integer productid);

}
