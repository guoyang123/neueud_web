package com.neuedu;

import com.neuedu.cache.TokenCache;
import com.neuedu.common.IpUtils;
import com.neuedu.dao.IUserDao;
import com.neuedu.dao.impl.UserDaoImpl;
import com.neuedu.pojo.UserInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.UUID;


public class UserDaoImpTest {


    IUserDao userDao;

   @Before
   public  void  before(){
        System.out.println("===before==");
        userDao=new UserDaoImpl();
   }

    @Test
    public  void   testLogin() throws SocketException, UnknownHostException {

        UserInfo userInfo= userDao.login("admin","admin");
        System.out.println(userInfo);

        //String mac=IpUtils.getMACAddress("10.25.131.138");
        String mac=IpUtils.getMACAddress("127.0.0.1");
        System.out.println("mac="+mac);
    }
    @Test
    public  void   testUpdateTokenById() throws SocketException, UnknownHostException {

        System.out.println( userDao.updateTokenById(21,"abc"));


    }


    @Test
    public  void   testFindUserInfoByToken() throws SocketException, UnknownHostException {

        System.out.println( userDao.findUserInfoByToken("ad6094fa05eec3b2e03ff44cdfc659a1"));


    }


    @Test
    public  void   testCheckUserName() throws SocketException, UnknownHostException {

       // System.out.println( userDao.checkUserName("adminsdfsaf"));

        System.out.println( userDao.checkEmail("xxx@126.com"));

    }
    @Test
    public void testFindQuestionByUsername(){
        System.out.println(userDao.findQuestionByUsername("admin"));
    }

    @Test
    public void  tesTcheckAnswer(){
        System.out.println( userDao.checkAnswer("root","xxx","xxxxx"));

     //   System.out.println(UUID.randomUUID().toString());

    }

    @Test
    public  void  testCache(){

        TokenCache.set("hello","world");
        System.out.println(TokenCache.get("hello"));
    }
    @After
    public  void  destory(){
        System.out.println("===after==");
        userDao=null;
    }

    @Test
    public  void  testupdatePassword(){


        System.out.println( userDao.updatePassword("root","123"));

    }

}
