package cn.tedu.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "BMIServlet",urlPatterns = "/bmi")
public class BMIServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String h = request.getParameter("h");
        String w = request.getParameter("w");
        System.out.println(h+":"+w);
        float height = Float.parseFloat(h);
        float weight = Float.parseFloat(w);
        float bmi = weight/(height*height);
        System.out.println("bmi="+bmi);
        String info = null;
        if (bmi<18.5){
            info="太瘦了";
        }else if (bmi<=20){
            info = "恭喜你！体重正常";
        }else if (bmi<=28){
            info = "微胖";
        }else {
            info = "肥胖";
        }
        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.print(info);
        pw.close();
    }
}
