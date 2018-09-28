package com.neuedu.common;

//创建枚举类
public enum ResponseCode {

    //成功
    SUCCESS(0,"成功"),
     //需要登录
      NEED_LOGIN(1,"需要登录"),
     NO_PERMISSION(2,"无权限"),
    //获取子类别，categoryId必须传
     GETSUBCATEGORY_NEED_CATEGORYID(3,"categoryId必须"),
    GETSUBCATEGORY_NEED_CATEGORYNAME(4,"类别名称必须"),
    NEED_PRODUCT(5,"商品参数必需"),
     //接口返回失败
     NEED_PRODUCT_STATUS(7,"商品status参数必需"),
     PRODUCT_OFFLINE(8,"商品不存在或者已经下架"),
     NEED_ORDERNO(9,"需要订单号"),
    NOT_FOUND_ORDERNO(10,"没有该订单"),
    CART_EMPTY(11,"购物车空"),
    PRODUCT_OFFLINE_NOT_EXISTS(12,"商品下架或者不存在"),
    PRODUCT_STOCK_LESS(13,"商品库存不足"),
    FAIL(100,"失败");



     private  int  code;
     private  String  msg;
     private  ResponseCode(int code,String msg){
         this.code=code;
         this.msg=msg;
     }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
