package cn.tedu.controller;

import cn.tedu.dao.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LikeServlet",urlPatterns = "/like")
public class LikeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        //获取Session对象
        HttpSession session = request.getSession();
        //获取Session里面保存的作品id信息
        String likeId = (String) session.getAttribute("like"+id);
        //如果likeId为null 说明没有点过赞
        if (likeId==null){
            ProductDao dao = new ProductDao();
            dao.likeById(id);
            //把点过赞的id保存进session对象中
            session.setAttribute("like"+id,id);
        }

        response.sendRedirect("/detail?id="+id);
    }
}
