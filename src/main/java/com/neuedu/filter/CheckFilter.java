package com.neuedu.filter;

import com.neuedu.businessconst.Const;
import com.neuedu.pojo.UserInfo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//http://localhost:8080/BusinessWeb/manage/product.jsp
@WebFilter("/manage/*")
public class CheckFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request=(HttpServletRequest)req;
        HttpServletResponse response=(HttpServletResponse)resp;
        HttpSession httpSession=request.getSession();
        Object object=httpSession.getAttribute(Const.CURRENTUSER);
        if(object!=null&&object instanceof UserInfo){//获取登录信息
            chain.doFilter(req, resp);
        }else{//未登录
            response.sendRedirect("http://localhost:8080/BusinessWeb/login.jsp");
        }


    }

    public void init(FilterConfig config) throws ServletException {

    }

}
