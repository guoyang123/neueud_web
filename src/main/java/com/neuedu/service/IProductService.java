package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Product;
import com.neuedu.vo.PageModel;
import com.neuedu.vo.ProductVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductService {


    /**
     * 添加或更新商品
     * */

    public ServerResponse<String> addorupdateProduct(Product product);
/**
 *
 * 商品上下架
 * */
public  ServerResponse<String> onlineoroffline(Integer productId,Integer status);

/**
 * 查询商品接口
 * */
 public ServerResponse<ProductVO> findProductById(Integer productId);

 /**
  * 分页查询
  * */
 public  ServerResponse<PageModel<ProductVO>>  findProductByPageNo(Integer pageNo, Integer pageSize);


 /**
  * 后台模糊查询商品
  * */
 public  ServerResponse<PageModel<ProductVO>> findProductsByProductIdOrProductName(Integer productId,String productName,Integer pageNo,Integer pageSize);



    /**图片上传*/
    public ServerResponse<String> upload(MultipartFile upload);


    /**
     *
     * 前台接口，查询商品详情
     * @param productId 商品id
     * */
    public  ServerResponse<ProductVO>  findProductDetail(Integer productId);

    /**
     * 前台搜索商品
     * */
    public  ServerResponse<PageModel<ProductVO>> searchProducts(Integer categoryId,String productName,Integer pageNo,Integer pageSize,String  orderby);



}
