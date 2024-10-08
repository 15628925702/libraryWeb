package com.myself.controller;

import com.myself.entity.Admin;
import com.myself.entity.Book;
import com.myself.entity.Borrow;
import com.myself.entity.Reader;
import com.myself.service.BookService;
import com.myself.service.LoginService;
import com.myself.service.impl.BookServiceImpl;
import com.myself.service.impl.LoginServiceImpl;
import javax.servlet.ServletException; // 修改为 javax.servlet
import javax.servlet.annotation.WebServlet; // 修改为 javax.servlet.annotation
import javax.servlet.http.HttpServlet; // 修改为 javax.servlet.http
import javax.servlet.http.HttpServletRequest; // 修改为 javax.servlet.http
import javax.servlet.http.HttpServletResponse; // 修改为 javax.servlet.http
import javax.servlet.http.HttpSession; // 修改为 javax.servlet.http

import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private LoginService loginService = new LoginServiceImpl();


    // 处理登录业务逻辑
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String psssword = req.getParameter("password");
        String type = req.getParameter("type");
        Object object = loginService.login(username, psssword, type);
        if (object != null) {
            HttpSession session = req.getSession();
            switch (type) {
                case "reader":
                    Reader reader = (Reader) object;
                    session.setAttribute("reader", reader);
                    resp.sendRedirect("/book?page=1");
                    break;
                case "admin":
                    Admin admin = (Admin) object;
                    session.setAttribute("admin", admin);
                    resp.sendRedirect("/admin?method=findAllBorrow&page=1");
                    break;
            }
        } else {
            resp.sendRedirect("login.jsp");
        }
    }


}
