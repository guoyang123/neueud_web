package com.neuedu.dao;

import com.neuedu.pojo.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICartDao {

    /**
     * 根据userid和productid查询购物车中的购物信息
     * */

    public Cart findCartByUseridAndProductid( Integer userid,Integer productid);

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



   /**
    * 移出购物车中的商品信息
    * */
   public Integer deletProducts(List<Integer> productIds,Integer userid);


   /**
    * 选中购物车某个商品
    * */
   public Integer checkedProductByProductId(Integer userid,Integer productid);
    /**
     * 取消选中购物车某个商品
     * */
    public Integer uncheckedProductByProductId(Integer userid,Integer productid);

    /**
     * 查询用户已选中的购物信息
     * */
    public  List<Cart> findCheckedCartsByUserId(Integer userid);

  /**
   * 下单成功后，移出购物车中已经下单的商品
   * */
  public  Integer  removeCheckedProduct(List<Cart> cartList,Integer userid);

}
