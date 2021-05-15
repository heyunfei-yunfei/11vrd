package cn.tedu.controller;

import cn.tedu.dao.BannerDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "DeleteBannerServlet",urlPatterns ="/deletebanner")
public class DeleteBannerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        System.out.println(id);
        BannerDao dao = new BannerDao();
        System.out.println(id);
        String fileUrl=dao.findUrlById(id);
        System.out.println("删除的文件路径："+fileUrl);
        String path = request.getServletContext().getRealPath(fileUrl);
        System.out.println("完整路径："+path);
        new File(path).delete();//删除文件
        dao.deleteById(id);
        response.sendRedirect("/showbanner");

    }
}
