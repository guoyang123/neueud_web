package com.neuedu.dao.impl;

import com.neuedu.common.MyBatisUtil;
import com.neuedu.dao.IUserDao;
import com.neuedu.pojo.UserInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserDaoMyBatisImpl implements IUserDao {
    @Override
    public UserInfo login(String usernmae, String password) {
        SqlSession sqlSession= MyBatisUtil.getSqlSession();
        Map<String,String> map=new HashMap<>();
        map.put("username",usernmae);
        map.put("password",password);
        UserInfo userInfo=sqlSession.selectOne("com.neuedu.dao.IUserDao.login",map);
        sqlSession.close();
       return userInfo;
    }

    @Override
    public List<UserInfo> findAll() {

       SqlSession sqlSession= MyBatisUtil.getSqlSession();
       List<UserInfo> list=sqlSession.selectList("com.neuedu.dao.IUserDao.findAll");
       sqlSession.close();
        return list;
    }

    @Override
    public int updateTokenById(int userid, String token) {
        return 0;
    }

    @Override
    public UserInfo findUserInfoByToken(String token) {
        return null;
    }

    @Override
    public int checkUserName(String username) {

       SqlSession sqlSession= MyBatisUtil.getSqlSession();
       int result=sqlSession.selectOne("com.neuedu.dao.IUserDao.checkUsername",username);
          sqlSession.close();
        return result;
    }

    @Override
    public int checkEmail(String email) {
        return 0;
    }

    @Override
    public int register(UserInfo userInfo) {

      SqlSession sqlSession= MyBatisUtil.getSqlSession();
       int  result= sqlSession.insert("com.neuedu.dao.IUserDao.register",userInfo);
       sqlSession.commit();// insert delete update  需要手动提交事务
       sqlSession.close();
       return   result;
    }

    @Override
    public String findQuestionByUsername(String username) {
        return null;
    }

    @Override
    public int checkAnswer(String username, String question, String answer) {
        return 0;
    }

    @Override
    public int updatePassword(String username, String newPassword) {
        return 0;
    }

    @Override
    public List<UserInfo> findAllByUsername(String username) {

        //
        SqlSession sqlSession= MyBatisUtil.getSqlSession();
       return  sqlSession.selectList("com.neuedu.dao.IUserDao.findAllByUsername",username);

    }

    @Override
    public UserInfo findByOption(UserInfo userInfo) {
        SqlSession sqlSession= MyBatisUtil.getSqlSession();

       // findAllByOption
        return  sqlSession.selectOne("com.neuedu.dao.IUserDao.findAllByOption",userInfo);


    }

    @Override
    public int updateUser(UserInfo userInfo) {

        SqlSession sqlSession= MyBatisUtil.getSqlSession();
        int result=sqlSession.update("com.neuedu.dao.IUserDao.updateUser",userInfo);
        sqlSession.commit();
        return result;
    }

    @Override
    public List<UserInfo> findByIds(List<Integer> ids) {
        SqlSession sqlSession= MyBatisUtil.getSqlSession();
        return sqlSession.selectList("com.neuedu.dao.IUserDao.findByIds",ids);

    }

    @Override
    public int insertUsers(List<UserInfo> userInfoList) {
        return 0;
    }
}
