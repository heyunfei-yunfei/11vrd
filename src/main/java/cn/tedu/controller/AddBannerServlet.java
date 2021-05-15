package cn.tedu.controller;

import cn.tedu.dao.BannerDao;
import cn.tedu.dao.ProductDao;
import cn.tedu.entity.Banner;
import cn.tedu.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
@MultipartConfig
@WebServlet(name = "AddBannerServlet",urlPatterns = "/addbanner")
public class AddBannerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Part part = request.getPart("file");
        String info = part.getHeader("content-disposition");
        //获取文件后缀名
        String suffix = info.substring(info.lastIndexOf("."),info.length()-1);
        String fileName = UUID.randomUUID()+suffix;
        System.out.println("文件名："+fileName);
        String path = request.getServletContext().getRealPath("images/"+fileName);
        part.write(path);
        Banner banner = new Banner(0,"images/"+fileName);
        BannerDao dao = new BannerDao();
        dao.insert(banner);
        response.sendRedirect("/showbanner");


    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
