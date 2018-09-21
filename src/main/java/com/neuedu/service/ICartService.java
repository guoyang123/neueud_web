package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.vo.CartVO;

public interface ICartService {

    /**
     * 添加商品到购物车
     *
     * */

    public ServerResponse<CartVO> addProductToCart(Integer userid,Integer productid,int count);

}
