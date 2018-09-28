package com.neuedu.service.impl;

import com.neuedu.businessconst.Const;
import com.neuedu.common.BigDecimalUtils;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.ICartDao;
import com.neuedu.dao.IProductDao;
import com.neuedu.pojo.Cart;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICartService;
import com.neuedu.vo.CartProductVO;
import com.neuedu.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {


    @Autowired
    private ICartDao cartDao;
    @Autowired
    private IProductDao productDao;

    @Override
    public ServerResponse<CartVO> addProductToCart(Integer userid, Integer productid, int count) {

         //step1:根据userid和productid查询购物车cart
          Cart cart=cartDao.findCartByUseridAndProductid(userid,productid);

        //step2: cart  null 添加 否则 更新
         if(cart==null){//添加

              Cart cart1=new Cart( userid, productid, count,1);
              cartDao.addProductToCart(cart1);

         }else{//更新
            cart.setQuantity(cart.getQuantity()+count);
            cartDao.updateCartByUseridAndProductid(cart);

         }

        CartVO cartVO= getCartVOLimit(userid);
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),cartVO);
    }

    @Override
    public ServerResponse<CartVO> findCartsByUserId(Integer userid) {

        //step1:根据userid查询用户购物车信息
       CartVO cartVO=  getCartVOLimit(userid);

        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),cartVO);

    }

    @Override
    public ServerResponse<CartVO> updateCartByUseridAndProductid(Integer userid, Integer productid, Integer quantity) {
        //step1:参数的非空验证
        if(productid==null||quantity==null){
            return  ServerResponse.createServerResponse(ResponseCode.NEED_PRODUCT.getCode(),ResponseCode.NEED_PRODUCT.getMsg());
        }
      //setp2:构造cart
        Cart cart=new Cart();
        cart.setUser_id(userid);
        cart.setProduct_id(productid);
        cart.setQuantity(quantity);
        cartDao.updateCartByUseridAndProductid(cart);
        CartVO cartVO=getCartVOLimit(userid);
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),cartVO);
    }

    @Override
    public ServerResponse<CartVO> deleteProductsFromCart(Integer userid, String productIds) {
        //step1:参数的非空验证
        if(productIds==null||productIds.equals("")){
            return  ServerResponse.createServerResponse(ResponseCode.NEED_PRODUCT.getCode(),ResponseCode.NEED_PRODUCT.getMsg());
        }

        //step2: productIds   1,2,3
        String[] productidstrarry=productIds.split(",");
        List<Integer> productIdsList=new ArrayList<>();
        for(String productidStr:productidstrarry){
           Integer productid= Integer.parseInt(productidStr);
           productIdsList.add(productid);
        }
       cartDao.deletProducts(productIdsList,userid);
        CartVO cartVO=getCartVOLimit(userid);
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),cartVO);
    }

    @Override
    public  ServerResponse<CartVO>  checkedProductByProductId(Integer userid, Integer productid) {
        //step1:参数的非空验证
        if(productid==null){
            return  ServerResponse.createServerResponse(ResponseCode.NEED_PRODUCT.getCode(),ResponseCode.NEED_PRODUCT.getMsg());
        }
        cartDao.checkedProductByProductId(userid,productid);

        CartVO cartVO=getCartVOLimit(userid);
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),cartVO);
    }

    @Override
    public  ServerResponse<CartVO>  uncheckedProductByProductId(Integer userid, Integer productid) {
        //step1:参数的非空验证
        if(productid==null){
            return  ServerResponse.createServerResponse(ResponseCode.NEED_PRODUCT.getCode(),ResponseCode.NEED_PRODUCT.getMsg());
        }
        cartDao.uncheckedProductByProductId(userid,productid);

        CartVO cartVO=getCartVOLimit(userid);
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),cartVO);
    }


    public  CartVO getCartVOLimit(Integer userid){

        CartVO cartVO=new CartVO();

        //step1:根据userid查询购物车商品集合
        List<Cart> cartList= cartDao.findCartsByUserid(userid);
        //step2:将cartList转成List<CartProductVO>
        List<CartProductVO> cartProductVOList=new ArrayList<>();
        BigDecimal carttotalPrice=new BigDecimal(0);
        if(cartList!=null&&cartList.size()>0){
            for(Cart cart:cartList){

                CartProductVO cartProductVO=new CartProductVO();
               Product product= productDao.findProductById(cart.getProduct_id());

               cartProductVO.setUser_id(userid);

               cartProductVO.setProductid(cart.getProduct_id());
               cartProductVO.setName(product.getName());
               cartProductVO.setSubtitle(product.getSubtitle());
               cartProductVO.setMain_image(product.getMain_image());
               cartProductVO.setProductprice(product.getPrice());
               //设置商品总价格
               cartProductVO.setTotalPrice(BigDecimalUtils.multiply(product.getPrice(),new BigDecimal(cart.getQuantity())));

               if(cart.getChecked()==Const.CHECKENUM.CHECKED.getCode()){
                   carttotalPrice=BigDecimalUtils.add(carttotalPrice,cartProductVO.getTotalPrice());
               }

                //校验库存
                if(product.getStock()>cart.getQuantity()){
                    //库存充足
                    cartProductVO.setLimitQuantity(Const.STOCK.LIMIT_NUM_SUCCESS.getStockdesc()); //LIMIT_NUM_SUCCESS LIMIT_NUM_FAIL
                }else{

                   //     5
                    cartProductVO.setLimitQuantity(Const.STOCK.LIMIT_NUM_FAIL.getStockdesc()); //LIMIT_NUM_SUCCESS LIMIT_NUM_FAIL
                     //更新购物车中商品的购买数量
                    Cart cart1=new Cart();
                    cart1.setUser_id(userid);
                    cart1.setQuantity(product.getStock());
                    cart1.setProduct_id(product.getId());
                    cart.setQuantity(product.getStock());
                    cartDao.updateCartByUseridAndProductid(cart1);
                }

                cartProductVO.setQuantity(cart.getQuantity());
                cartProductVO.setStatus(product.getStatus());
                cartProductVO.setCartid(cart.getId());
                //是否被勾选
                cartProductVO.setChecked(cart.getChecked());
                cartProductVOList.add(cartProductVO);
            }
        }

        cartVO.setCartProductVOList(cartProductVOList);
        cartVO.setIsallchecked(cartDao.isCheckAll(userid)==0);
        cartVO.setTotalPrice(carttotalPrice);


      return  cartVO;
    }


}
