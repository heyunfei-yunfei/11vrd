package cn.tedu.controller;

import cn.tedu.dao.CategoryDao;
import cn.tedu.dao.ProductDao;
import cn.tedu.entity.Category;
import cn.tedu.entity.Product;
import cn.tedu.utils.ThUtils;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DetailServlet",urlPatterns = "/detail")
public class DetailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        System.out.println("详情id="+id);
        //通过id 查询出此id对应的作品详情
        ProductDao dao = new ProductDao();

        //浏览量+1
        //获取Session对象
        HttpSession session = request.getSession();
        //获取Session里面保存的作品id信息
        String viewId = (String) session.getAttribute("view"+id);
        //如果viewId为null 说明没有浏览过
        if (viewId==null) {
            //把浏览过的id保存进session对象中
            session.setAttribute("view" + id, id);
            dao.viewById(id);
        }

        Product product = dao.findById(id);
        System.out.println("查询到的作品信息："+product);
        //把查询到的信息装进容器中在detail页面中显示
        Context context = new Context();
        context.setVariable("product",product);
        //分类
        CategoryDao cDao = new CategoryDao();
        List<Category> list = cDao.findAll();
        context.setVariable("list",list);
        //浏览最多
        context.setVariable("vList",dao.fidndViewList());
        //最受欢迎
        context.setVariable("lList",dao.findLikeList());
        //把登陆的用户对象传递到页面中
        context.setVariable("user",request.getSession().getAttribute("user"));

        ThUtils.print("detail.html",context,response);
    }
}
