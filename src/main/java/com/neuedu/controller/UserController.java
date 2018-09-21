package com.neuedu.controller;

import com.google.gson.Gson;
import com.neuedu.businessconst.Const;
import com.neuedu.common.IpUtils;
import com.neuedu.common.MD5Utils;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.exception.BusinessException;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    IUserService userService;
    @RequestMapping(value = "/logout")
    public ServerResponse<UserInfo> logout( HttpSession session){

        //step1：校验是否登录
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo!=null){
            session.removeAttribute(Const.CURRENTUSER);
        }
        //token清空

        if(userInfo!=null){
            userService.updateTokenById(userInfo.getId(),"");
        }

      return ServerResponse.createServerResponse(0,"成功");
    }
    //获取用户信息
    @RequestMapping("/userinfo")
    public  ServerResponse<UserInfo> getuserinfo(HttpSession session){

        //step1：校验是否登录
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){
            //登录过期或未登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        System.out.println("====userinfo="+userInfo);
        return ServerResponse.createServerResponse(0,"成功",userInfo);
    }
    //登录状态下重置密码
    @RequestMapping(value = "/setpassword")
    public   ServerResponse<UserInfo>  updatePasswordInLogin(@RequestParam(value = "oldpassword") String oldpassword
            ,@RequestParam(value = "newpassword") String newpassword,HttpSession session) {
        if(oldpassword==null||oldpassword.equals("")){

            return ServerResponse.createServerResponse(1,"旧密码不能为空");
        }
        if(newpassword==null||newpassword.equals("")){


            return ServerResponse.createServerResponse(2,"新密码不能为空");
        }

        //调用service

        //step1:判断用户是否登录
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo==null){//未登录或者已经过期
            return ServerResponse.createServerResponse(5,"未登录或者已经过期");

        }

        return userService.updatePassword(oldpassword,newpassword,userInfo);

    }

    @RequestMapping(value = "/updatepassword")
    public   ServerResponse<String>  updatePassword(String  username,String newpassword,String token ) throws ServletException, IOException {



        if(username==null||username.equals("")){

            return ServerResponse.createServerResponse(1,"用户名不能为空");

        }
        if(newpassword==null||newpassword.equals("")){
            return ServerResponse.createServerResponse(2,"密码不能为空");

        }
        if(token==null||token.equals("")){
            return ServerResponse.createServerResponse(3,"token不能为空");

        }

        //调用service

        return userService.updatePassword(username,newpassword,token);



    }


    @RequestMapping(value = "/checkanswer")
    public   ServerResponse<String>  checkAnswer(String username,String question,String answer) throws ServletException, IOException {



        if(username==null||username.equals("")){
            return ServerResponse.createServerResponse(1,"用户名不能为空");

        }
        if(question==null||question.equals("")){
            return ServerResponse.createServerResponse(2,"问题不能为空");


        }
        if(answer==null||answer.equals("")){
            return ServerResponse.createServerResponse(3,"答案不能为空");
        }

        //调用service

       return userService.checkAnswer(username,question,answer);


    }


    @RequestMapping(value = "/findquestion")
    public   ServerResponse<String>  findQuestionByUsername(String username,HttpSession session){


        //step1:非空验证

        if(username==null||username.equals("")){

            return ServerResponse.createServerResponse(1,"用户名不能为空");

        }
        //step2:调用service层

        return  userService.findQuestionByUsername(session,username);



    }


    @RequestMapping(value = "/register")
    public  ServerResponse<UserInfo> register(String  username,String password,String email,String phone,
                                              String question,String answer){


//http://localhost:8080/BusinessWeb/user?operation=2&username=root&password=root&email=23232@qq.com&phone=1223434&question=xxx&answer=xxxx
        //http://localhost:8080/BusinessWeb/user?operation=2&username=root&password=root&email=23232@qq.com&phone=1223434&question=xxx&answer=xxxx
        if(username==null||username.equals("")){

             return ServerResponse.createServerResponse(1,"用户名不能为空");
        }
        if(password==null||password.equals("")){
            return ServerResponse.createServerResponse(2,"密码不能为空");
           }
        if(email==null||email.equals("")){
            return ServerResponse.createServerResponse(3,"邮箱不能为空");
         }
        if(phone==null||phone.equals("")){
            return ServerResponse.createServerResponse(4,"手机号不能为空");
        }
        if(question==null||question.equals("")){
            return ServerResponse.createServerResponse(5,"找回密码问题不能为空");
          }
        if(answer==null||answer.equals("")){
            return ServerResponse.createServerResponse(6,"找回密码的问题的答案不能为空");
            }




        UserInfo userInfo=new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        userInfo.setEmail(email);
        userInfo.setPhone(phone);
        userInfo.setQuestion(question);
        userInfo.setAnswer(answer);
       return userService.register(userInfo);



    }



    @RequestMapping(value = "/login")
    public  ServerResponse<UserInfo>   login(String username,String password,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException, ServletException {

        //非空判断
        if(username==null||username.equals("")||password==null||password.equals("")){
            return ServerResponse.createServerResponse(2,"用户名或密码不能为空");
        }

        ServerResponse<UserInfo>  serverResponse=userService.login(username,password);
        UserInfo userInfo=serverResponse.getData();
        if(userInfo!=null){//查询成功

            //获取到ip
            String ip= IpUtils.getRemoteAddress(request);
            //获取mac
            String macAddress=IpUtils.getMACAddress(ip);
            //mac加密
            String  token= MD5Utils.GetMD5Code(macAddress);
            Cookie cookie=new Cookie(Const.AUTOLOGINTOKEN,token);
            cookie.setMaxAge(60*60*24*7);
            response.addCookie(cookie);
            //token保存到数据库
            userService.updateTokenById(userInfo.getId(),token);


            session.setAttribute(Const.CURRENTUSER,userInfo);

            return  serverResponse;


        }else{//用户名或密码错误


          return ServerResponse.createServerResponse(3,"用户名或密码错误");
        }


    }






}
