package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;

import javax.servlet.http.HttpSession;

public interface IUserService {

    /**
     * 登录
     * */
    public ServerResponse<UserInfo> login(String username,String password);

    /**
     * 修改token
     * @Param userid 用户id
     * @param token 用户token
     */

    public int updateTokenById(int userid, String token);

    //根据token查询用户信息
    public UserInfo findUserInfoByToken(String token);


    public  ServerResponse<UserInfo>  register(UserInfo userInfo);

    public  ServerResponse<String>   findQuestionByUsername(HttpSession session,String username);

    //校验答案
    public ServerResponse<String>    checkAnswer(String username,String question,String answer);

    //修改密码
    public  ServerResponse<String>  updatePassword(String username,String newpassword,String  token);

    //登录状态下修改密码
    public  ServerResponse<UserInfo>  updatePassword(String oldpassword,String newpassword,UserInfo userInfo);
   // public  ServerResponse<UserInfo> updatePassword();

    //检查用户管理员权限
    public  boolean  isAdminRole(UserInfo userInfo);

}
