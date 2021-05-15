package cn.tedu.controller;

import cn.tedu.utils.ThUtils;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ShowLoginServlet",urlPatterns = "/showlogin")
public class ShowLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //显示登陆页面
        Context context = new Context();
        //取出Cookie里面的用户名和密码
        Cookie[] cookies = request.getCookies();
        //首先进行非空判断
        if (cookies!=null){
            //遍历客户端传递过来的所有Cookie
            for (Cookie c : cookies) {
                //取出Cookie中保存的名和值
                String name = c.getName();
                String value = c.getValue();
                //判断如果是用户名 保存到容器中
                if (name.equals("username")){
                    context.setVariable("username",value);
                }else if(name.equals("password")){//判断如果是密码 也保存进容器
                    context.setVariable("password",value);
                }
            }
        }
        ThUtils.print("login.html",context,response);
    }
}
