package cn.tedu.controller;

import cn.tedu.dao.UserDao;
import cn.tedu.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet",urlPatterns = "/loginaction")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置字符集
        request.setCharacterEncoding("UTF-8");
        //获取参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username+":"+password);
        //创建Dao并调用登陆方法
        UserDao dao = new UserDao();
        User user = dao.login(username,password);
        //判断是否登录成功
        if (user!=null){//登录成功!
            //判断是否需要记住用户名和密码
            String rem = request.getParameter("rem");
            System.out.println("rem="+rem);
            if (rem!=null){//需要记住用户名和密码
                //创建Cookie 把用户名和密码装进Cookie
                Cookie c1 = new Cookie("username",username);
                Cookie c2 = new Cookie("password",password);
                //把cookie下发到客户端浏览器
                response.addCookie(c1);
                response.addCookie(c2);
            }
            //通过Session记住登录状态
            //getSession()方法 如果通过SessionId找到了曾经创建的Session对象
            //会直接返回, 如果没有找到则创建一个新的并返回
            HttpSession session = request.getSession();
            //把user对象装进Session里面
            session.setAttribute("user",user);


            //重定向到首页
            response.sendRedirect("/home");
        }else{//登录失败
            //重定向回登录页面
            response.sendRedirect("/showlogin");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
