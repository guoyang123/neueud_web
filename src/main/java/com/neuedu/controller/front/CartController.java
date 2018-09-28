package com.neuedu.controller.front;

import com.neuedu.businessconst.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.ICartService;
import com.neuedu.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    private ICartService cartService;
    @RequestMapping(value = "/add")
    public ServerResponse<CartVO> add(Integer productId,
                                      Integer count, HttpSession session){

        //step1: 判定用户是否登录
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){//没有登录
            return  ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
    return  cartService.addProductToCart(userInfo.getId(),productId,count);

    }

 /**
  * 查询购物车列表
  * */
    @RequestMapping("/list.do")
    public   ServerResponse<CartVO> findCartsByuserid(HttpSession session){
        //step1: 判定用户是否登录
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){//没有登录
            return  ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
     return  cartService.findCartsByUserId(userInfo.getId());
    }


    /**
     * 更新购物车中某个商品的数量
     * */
    @RequestMapping("/update.do")
    public   ServerResponse<CartVO> updateCartByUseridAndProductId(HttpSession session,
                                                                   Integer productId,Integer quantity){
        //step1: 判定用户是否登录
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){//没有登录
            return  ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return  cartService.updateCartByUseridAndProductid(userInfo.getId(),productId,quantity);
    }

/**
 * 删除购物车中某些商品
 * */
    @RequestMapping("/delete.do")
    public   ServerResponse<CartVO> deleteProductsFromCart(HttpSession session,
                                                                   String productIds){
        //step1: 判定用户是否登录
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){//没有登录
            return  ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return  cartService.deleteProductsFromCart(userInfo.getId(),productIds);
    }

    /**
     * 选中购物车中某个商品
     * */
    @RequestMapping("/check.do")
    public   ServerResponse<CartVO> checkedProduct(HttpSession session,
                                                           Integer productid){
        //step1: 判定用户是否登录
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){//没有登录
            return  ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return  cartService.checkedProductByProductId(userInfo.getId(),productid);
    }
    /**
     * 取消选中购物车中某个商品
     * */
    @RequestMapping("/uncheck.do")
    public   ServerResponse<CartVO> uncheckedProduct(HttpSession session,
                                                   Integer productid){
        //step1: 判定用户是否登录
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){//没有登录
            return  ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return  cartService.uncheckedProductByProductId(userInfo.getId(),productid);
    }

}
