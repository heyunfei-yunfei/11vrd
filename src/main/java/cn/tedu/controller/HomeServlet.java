package cn.tedu.controller;

import cn.tedu.dao.BannerDao;
import cn.tedu.dao.CategoryDao;
import cn.tedu.dao.ProductDao;
import cn.tedu.entity.Banner;
import cn.tedu.entity.Category;
import cn.tedu.entity.Product;
import cn.tedu.entity.User;
import cn.tedu.utils.ThUtils;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet",urlPatterns = "/home")
public class HomeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取传递过来的参数
        String cid = request.getParameter("cid");
        System.out.println("分类id="+cid);
        String keyword = request.getParameter("keyword");
        System.out.println("搜索关键字"+keyword);



        Context context = new Context();
        //创建CategoryDao 并调用查询所有分类的方法
        CategoryDao dao = new CategoryDao();
        List<Category> list = dao.findAll();
        context.setVariable("list",list);
        //创建BannerDao并查询所有Banner
        BannerDao bDao = new BannerDao();
        List<Banner> bList = bDao.findAll();
        System.out.println("轮播图:"+bList);
        context.setVariable("bList",bList);

        //取出Session里面的用户对象
        User user = (User) request.getSession().getAttribute("user");
//        if (user!=null){
//            System.out.println("曾经登录过");
//        }else{
//            System.out.println("没有登录过");
//        }
        context.setVariable("user",user);
        //查询所有作品 并且把查询到的内容装进容器
        ProductDao pDao = new ProductDao();
        //如果cid的值 代表查询到的是某个分类下的作品信息
        if (cid!=null){
            List<Product> pList = pDao.findByCid(cid);
            context.setVariable("pList", pList);
        }else if (keyword!=null){//此页面执行的是查询操作
            List<Product> pList = pDao.findByKeyword(keyword);
            context.setVariable("pList",pList);
        } else {//如果没有参数则查询所有
            List<Product> pList = pDao.findAll();
            context.setVariable("pList", pList);
        }
        //查询浏览最多列表
        List<Product> vList = pDao.fidndViewList();
        context.setVariable("vList",vList);
        //查询最受欢迎列表
        List<Product> lList = pDao.findLikeList();
        context.setVariable("lList",lList);
        ThUtils.print("home.html",context,response);
    }
}
