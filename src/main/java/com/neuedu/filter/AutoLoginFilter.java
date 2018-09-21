package com.neuedu.filter;

import com.neuedu.businessconst.Const;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.service.impl.UserServiceImpl;

import javax.jws.soap.SOAPBinding;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/manage/*")
public class AutoLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {


        HttpServletRequest request=(HttpServletRequest)req;
        HttpServletResponse response=(HttpServletResponse)resp;


       HttpSession httpSession=  request.getSession();
      UserInfo userInfo=(UserInfo)httpSession.getAttribute(Const.CURRENTUSER);
      if(userInfo!=null){
          chain.doFilter(req, resp);
          return;
      }
       Cookie[] cookies= request.getCookies();
       String  token=null;

        if(cookies!=null){
            for(Cookie cookie:cookies){
                String cookieName=cookie.getName();
                if(cookieName.equals(Const.AUTOLOGINTOKEN)){
                   token=cookie.getValue();
                }

            }
        }

        if(token!=null){

            IUserService userService=new UserServiceImpl();
            userInfo= userService.findUserInfoByToken(token);
           if(userInfo!=null){//登录成功
             HttpSession session= request.getSession();
             session.setAttribute(Const.CURRENTUSER,userInfo);
               chain.doFilter(req, resp);
               return;
           }
        }


        response.sendRedirect("http://localhost:8080/BusinessWeb/login.jsp");

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
