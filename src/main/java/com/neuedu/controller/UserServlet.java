package com.neuedu.controller;

import com.google.gson.Gson;
import com.neuedu.businessconst.Const;
import com.neuedu.common.IpUtils;
import com.neuedu.common.MD5Utils;
import com.neuedu.exception.BusinessException;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.net.UnknownHostException;

//@WebServlet("/user")
public class UserServlet extends HttpServlet {
  /*  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doGet(request,response);
    }*/
/*
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

       response.setContentType("text/html;charset=UTF-8");

      String operation=request.getParameter("operation");
      if(operation==null||operation.equals("")){

          throw  BusinessException.createException(request.getSession(),"operation参数必需","3s后跳转到登录页面","login.jsp");
      }
      if(operation.equals("1")){//登录操作
          login(request,response);
      }else if(operation.equals("2")){//注册

          register(request,response);
      }else if(operation.equals("3")){//根据username查询问题
          findQuestionByUsername(request,response);
      }
      else if(operation.equals("4")){//校验答案
          checkAnswer(request,response);
      }else if(operation.equals("5")){//修改密码
          updatePassword(request,response);
      }else if(operation.equals("6")){//登录状态下重置密码
          updatePasswordInLogin(request,response);
      }else if(operation.equals("7")){//获取用户信息
          getuserinfo(request,response);
      }else if(operation.equals("8")){//退出登录
          logout(request,response);
      }


    }
    public  void logout(HttpServletRequest request,HttpServletResponse response){

        //step1：校验是否登录
        HttpSession session=request.getSession();
        UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if(userInfo!=null){
            session.removeAttribute(Const.CURRENTUSER);
        }

        //token清空
        IUserService userService=new UserServiceImpl();
        if(userInfo!=null){
            userService.updateTokenById(userInfo.getId(),"");
        }


    }
    public  void getuserinfo(HttpServletRequest request,HttpServletResponse response){

           //step1：校验是否登录
            HttpSession session=request.getSession();
            UserInfo userInfo=(UserInfo) session.getAttribute(Const.CURRENTUSER);
            if(userInfo==null){
                //登录过期或未登录
                throw BusinessException.createException(session,"未登录或者已经过期","3s后跳转到登录页面","login.jsp");
            }
        System.out.println("====userinfo="+userInfo);
}
    //登录状态下重置密码
    public   void  updatePasswordInLogin(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        HttpSession session=request.getSession();
        String oldpassword=request.getParameter("oldpassword");
        String newpassword=request.getParameter("newpassword");


        if(oldpassword==null||oldpassword.equals("")){

            throw  BusinessException.createException(session,"旧密码不能为空","3s后跳转到输入登录页面","login.jsp");
        }
        if(newpassword==null||newpassword.equals("")){

            throw  BusinessException.createException(session,"新密码不能为空","3s后跳转到输入登录页面","login.jsp");
        }


        //调用service
        IUserService userService=new UserServiceImpl();
        int result=userService.updatePassword(session,oldpassword,newpassword);

        if(result>0){//密码修改成功
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }else{
            throw  BusinessException.createException(session,"密码修改失败","3s后跳转到输入新密码页面","newpassword.jsp");
        }
    }

    public   void  updatePassword(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

        HttpSession session=request.getSession();
        String username= request.getParameter("username");
        String newpassword=request.getParameter("newpassword");
        String token=request.getParameter("token");

        if(username==null||username.equals("")){

            throw  BusinessException.createException(session,"用户名不能为空","3s后跳转到输入新密码页面","newpassword.jsp");
        }
        if(newpassword==null||newpassword.equals("")){

            throw  BusinessException.createException(session,"密码不能为空","3s后跳转到输入新密码页面","newpassword.jsp");
        }
        if(token==null||token.equals("")){

            throw  BusinessException.createException(session,"token不能为空","3s后跳转到输入新密码页面","newpassword.jsp");
        }

        //调用service
        IUserService userService=new UserServiceImpl();
         int result=userService.updatePassword(session,username,newpassword,token);

        if(result>0){//密码修改成功
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }else{
            throw  BusinessException.createException(session,"密码修改失败","3s后跳转到输入新密码页面","newpassword.jsp");

        }

    }


    public   void  checkAnswer(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

       HttpSession session=request.getSession();
        String username= request.getParameter("username");
        String question=request.getParameter("question");
        String answer=request.getParameter("answer");

        if(username==null||username.equals("")){

            throw  BusinessException.createException(session,"用户名不能为空","3s后跳转到找输入答案页面","answer.jsp");
        }
        if(question==null||question.equals("")){

            throw  BusinessException.createException(session,"用户名不能为空","3s后跳转到找输入答案页面","answer.jsp");
        }
        if(answer==null||answer.equals("")){

            throw  BusinessException.createException(session,"用户名不能为空","3s后跳转到找输入答案页面","answer.jsp");
        }

        //调用service
        IUserService userService=new UserServiceImpl();
        String token=userService.checkAnswer(session,username,question,answer);
        session.setAttribute("forget_token",token);
        request.getRequestDispatcher("newpassword.jsp").forward(request,response);

    }


      public   void  findQuestionByUsername(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

       String username= request.getParameter("username");

         HttpSession session= request.getSession();
       //step1:非空验证

          if(username==null||username.equals("")){

              throw  BusinessException.createException(session,"用户名不能为空","3s后跳转到找回密码页面","findPassword.jsp");
          }
          //step2:调用service层
          IUserService userService=new UserServiceImpl();
          String question=  userService.findQuestionByUsername(session,username);

           session.setAttribute("question",question);
           request.getRequestDispatcher("answer.jsp").forward(request,response);

      }


    public  void register(HttpServletRequest request,HttpServletResponse response){

        HttpSession session=request.getSession();
        //获取请求参数
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String email=request.getParameter("email");
        String phone=request.getParameter("phone");
        String question=request.getParameter("question");
        String anser=request.getParameter("answer");
//http://localhost:8080/BusinessWeb/user?operation=2&username=root&password=root&email=23232@qq.com&phone=1223434&question=xxx&answer=xxxx
        //http://localhost:8080/BusinessWeb/user?operation=2&username=root&password=root&email=23232@qq.com&phone=1223434&question=xxx&answer=xxxx
        if(username==null||username.equals("")){

            throw  BusinessException.createException(session,"用户名不能为空","3s后跳转到注册页面","register.jsp");
        }
        if(password==null||password.equals("")){

            throw  BusinessException.createException(session,"密码不能为空","3s后跳转到注册页面","register.jsp");
        }
        if(email==null||email.equals("")){

            throw  BusinessException.createException(session,"邮箱不能为空","3s后跳转到注册页面","register.jsp");
        }
        if(phone==null||phone.equals("")){

            throw  BusinessException.createException(session,"手机号不能为空","3s后跳转到注册页面","register.jsp");
        }
        if(question==null||question.equals("")){

            throw  BusinessException.createException(session,"找回密码的问题不能为空","3s后跳转到注册页面","register.jsp");
        }
        if(anser==null||anser.equals("")){

            throw  BusinessException.createException(session,"找回密码的问题的答案不能为空","3s后跳转到注册页面","register.jsp");
        }


       IUserService userService=new UserServiceImpl();

        UserInfo userInfo=new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        userInfo.setEmail(email);
        userInfo.setPhone(phone);
        userInfo.setQuestion(question);
        userInfo.setAnswer(anser);
        int  result=userService.register(session,userInfo);
        if(result>0){//注册成功
            throw  BusinessException.createException(session,"恭喜，注册成功","3s后跳转到登录页面","login.jsp");

        }else{//注册失败
            throw  BusinessException.createException(session,"ops,注册失败!!!","3s后跳转到注册页面","register.jsp");

        }



    }

    public  void   login(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        HttpSession httpSession= request.getSession();
        //接收视图层的参数，调用业务逻辑层处理
        //  username=xxx&password=xxx

        String username= request.getParameter("username");
        String password=request.getParameter("password");
        //非空判断
        if(username==null||username.equals("")||password==null||password.equals("")){

            throw  BusinessException.createException(httpSession,"用户名或密码不能为空","3s后跳转到登录页面","login.jsp")  ;

        }

        IUserService userService=new UserServiceImpl();
        UserInfo userInfo=userService.login(username,password);
        if(userInfo!=null){//查询成功

            //获取到ip
            String ip=IpUtils.getRemoteAddress(request);
            //获取mac
            String macAddress=IpUtils.getMACAddress(ip);
            //mac加密
            String  token= MD5Utils.GetMD5Code(macAddress);
            Cookie cookie=new Cookie(Const.AUTOLOGINTOKEN,token);
            cookie.setMaxAge(60*60*24*7);
            response.addCookie(cookie);
            //token保存到数据库
            userService.updateTokenById(userInfo.getId(),token);


            httpSession.setAttribute(Const.CURRENTUSER,userInfo);
            //  httpSession.setAttribute(Const.AUTOLOGINTOKEN,token);    jsp->java   html->不能识别java
         //   request.getRequestDispatcher("manage/home.jsp").forward(request,response);

            //json
            *//**
             *    {"id":21,"username":"admin"。。。}
             *     google   Gson
             *     alibaba  jackson
             * *//*
            Gson gson=new Gson();
             // javaobject-->json
           String  json= gson.toJson(userInfo);

            //json-->java object
            UserInfo user=gson.fromJson(json,UserInfo.class);

            //response.setHeader("Access-Control-Allow-Origin","*");
            //jsonp
       //  String call=  request.getParameter("callback");

            PrintWriter printWriter=response.getWriter();
            //调用客户端中js方法call
        //    printWriter.write(call+"("+json+")");//call(json)
            printWriter.write(json);


        }else{//用户名或密码错误


            System.out.println("===用户名或者密码错误=====");
        }


    }*/

}
