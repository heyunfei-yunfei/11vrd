package cn.tedu.controller;

import cn.tedu.dao.ProductDao;
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

//上传文件时 必须添加下面的注解
@MultipartConfig
@WebServlet(name = "SendServlet",urlPatterns = "/send")
public class SendServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置字符集
        request.setCharacterEncoding("UTF-8");
        //获取参数
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String intro = request.getParameter("intro");
        String categoryId = request.getParameter("categoryId");
        System.out.println(title+":"+author+":"+intro+":"+categoryId);
        //获取上传文件
        Part part = request.getPart("file");
        //获取文件后缀名
        String info = part.getHeader("content-disposition");
        String suffix = info.substring(info.lastIndexOf("."),info.length()-1);
        System.out.println("后缀名:"+suffix);
        //得到唯一的文件名
        String fileName = UUID.randomUUID()+suffix;
        System.out.println("文件名:"+fileName);
        //得到和日期相关路径    images/2021/2/18/xxxxx.jpg
        SimpleDateFormat format =
                new SimpleDateFormat(
                        "/yyyy/MM/dd/");
        //得到今天日期对象  导包 java.util
        Date date = new Date();
        //得到日期路径
        String datePath = format.format(date);
        System.out.println("日期路径:"+datePath);
        //得到Tomcat管辖范围内的路径
        String path = request.getServletContext()
                .getRealPath("images"+datePath);
        System.out.println(path);
        //创建出文件夹  一定要选择带s的方法
        new File(path).mkdirs();
        //把图片保存到文件夹中
        part.write(path+fileName);


        //把参数和图片路径封装到对象中
        //System.currentTimeMillis()获取当前的系统时间 距离1970年毫秒数
        Product p = new Product(0,title,author,intro,
                "images"+datePath+fileName,0,0,
                System.currentTimeMillis(),Integer.parseInt(categoryId));
        ProductDao dao = new ProductDao();
        dao.insert(p);
        //重定向到首页
        response.sendRedirect("/home");




    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
