package com.neuedu.dao.impl;

import com.neuedu.dao.ICartDao;
import com.neuedu.pojo.Cart;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CartDaoImpl implements ICartDao {

    @Autowired
    private SqlSession sqlSession;
    @Override
    public Cart findCartByUseridAndProductid(Integer userid, Integer productid) {

        Map<String,Integer> map=new HashMap<>();
        map.put("userid",userid);
        map.put("productid",productid);
        return sqlSession.selectOne("com.neuedu.dao.ICartDao.findCartByUseridAndProductid",map);
    }

    @Override
    public Integer addProductToCart(Cart cart) {
     //
        return sqlSession.insert("com.neuedu.dao.ICartDao.addProductToCart",cart);

    }

    @Override
    public Integer updateCartByUseridAndProductid(Cart cart) {


        return sqlSession.update("com.neuedu.dao.ICartDao.updateCartByUseridAndProductid",cart);

    }

    @Override
    public List<Cart> findCartsByUserid(Integer userid) {


        return sqlSession.selectList("com.neuedu.dao.ICartDao.findCartsByUserid",userid);

    }

    @Override
    public Integer isCheckAll(Integer userid) {

        return sqlSession.selectOne("com.neuedu.dao.ICartDao.isCheckAll",userid);

    }

    @Override
    public Integer deletProducts(List<Integer> productIds, Integer userid) {


        Map<String,Object> map=new HashMap<>();
        map.put("userid",userid);
        map.put("productIds",productIds);
       return  sqlSession.delete("com.neuedu.dao.ICartDao.deletProducts",map);



    }

    @Override
    public Integer checkedProductByProductId(Integer userid, Integer productid) {
        Map<String,Integer> map=new HashMap<>();
        map.put("userid",userid);
        map.put("productid",productid);
        return  sqlSession.delete("com.neuedu .dao.ICartDao.checkedProductByProductId",map);


    }

    @Override
    public Integer uncheckedProductByProductId(Integer userid, Integer productid) {
        Map<String,Integer> map=new HashMap<>();
        map.put("userid",userid);
        map.put("productid",productid);
        return  sqlSession.delete("com.neuedu.dao.ICartDao.uncheckedProductByProductId",map);

    }

    @Override
    public List<Cart> findCheckedCartsByUserId(Integer userid) {

        return sqlSession.selectList("com.neuedu.dao.ICartDao.findCheckedCartsByUserId",userid);

    }

    @Override
    public Integer removeCheckedProduct(List<Cart> cartList,Integer userid) {
        Map<String,Object> map=new HashMap<>();
        map.put("cartList",cartList);
        map.put("userid",userid);
        return sqlSession.delete("com.neuedu.dao.ICartDao.removeCheckedProduct",map);
    }
}
