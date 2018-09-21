package com.neuedu;

import com.neuedu.dao.IUserDao;
import com.neuedu.dao.impl.UserDaoMyBatisImpl;
import com.neuedu.pojo.UserInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MyBatisTest {

    @Test
    public  void  testLogin(){
        IUserDao userDao=new UserDaoMyBatisImpl();
        UserInfo userInfo=userDao.login("admin2","32cacb2f994f6b42183a1300d9a3e8d6");
        System.out.println(userInfo);
    }
    @Test
    public  void  testRegister(){
        IUserDao userDao=new UserDaoMyBatisImpl();
        //String username, String password, String email, String phone, String question, String answer, int role
        UserInfo userInfo=new UserInfo("admin5","admin5","xxx@qq.com","3434534","xxx","xxx",1);
        System.out.println(userDao.register(userInfo));
    }
    @Test
    public  void  testCheckUserName(){
        IUserDao userDao=new UserDaoMyBatisImpl();
        System.out.println( userDao.checkUserName("admin0"));
    }
    @Test
    public  void  testFindAll(){
        IUserDao userDao=new UserDaoMyBatisImpl();
        System.out.println( userDao.findAll());
    }


    @Test
    public  void  testFindAllByUsername(){
        IUserDao userDao=new UserDaoMyBatisImpl();
        System.out.println( userDao.findAllByUsername("admin2"));
    }

    @Test
    public  void  testFindAllByOption(){
        IUserDao userDao=new UserDaoMyBatisImpl();
        UserInfo userInfo=new UserInfo();
      //  userInfo.setUsername("admin2");
        userInfo.setId(23);
        userInfo.setEmail("xxxxx@qq.com");
        System.out.println( userDao.findByOption(userInfo));
    }


    @Test
    public  void  testUpdateUser(){
        IUserDao userDao=new UserDaoMyBatisImpl();
        UserInfo userInfo=new UserInfo();
        // userInfo.setUsername("admin2");
        userInfo.setId(23);
        userInfo.setEmail("xxxxxx@qq2.com");
        System.out.println( userDao.updateUser(userInfo));
    }

    @Test
    public  void  testFindByIds(){
        IUserDao userDao=new UserDaoMyBatisImpl();
        List<Integer> ids=new ArrayList<>();
        ids.add(23);
        ids.add(25);
        //System.out.println( userDao.findByIds(ids));



    }
@Test
    public  void   test(){

    System.out.println(cal(5));

    }

/**
 *
 *  n*(n-1)!
 *
 *  ....
 *  n*(n-1)(n-2)...1!
 *  自己调用自己:  递归算法
 *  一定要有结束的条件
 * */
    public  static int  cal(int n){

        if(n==1){
            return 1;
        }

        return  n*cal(n-1);
    }

}
