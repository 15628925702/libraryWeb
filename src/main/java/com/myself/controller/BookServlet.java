package com.myself.controller;

import com.myself.entity.Book;
import com.myself.entity.Borrow;
import com.myself.entity.Reader;
import com.myself.repository.impl.ReaderRepositoryImpl;
import com.myself.service.BookService;
import com.myself.service.impl.BookServiceImpl;
import jakarta.persistence.OneToMany;
import jakarta.persistence.criteria.CriteriaBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet("/book")
public class BookServlet extends HttpServlet {

    private BookService bookService = new BookServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method == null) {
            method = "findAll";
        }
        HttpSession session = req.getSession();
        Reader reader = (Reader) session.getAttribute("reader");
        switch(method) {
            case "findAll":
                String pageStr = req.getParameter("page");
                Integer page = Integer.parseInt(pageStr);
                List<Book> list = bookService.findAll(page);
                req.setAttribute("list", list);
                req.setAttribute("dataPrePage",6);
                req.setAttribute("currentPage",page);
                req.setAttribute("pages", bookService.getPages());
                req.getRequestDispatcher("index.jsp").forward(req, resp);
                break;
            case "addBorrow":
                String bookidStr = req.getParameter("bookid");
                Integer bookid = Integer.parseInt(bookidStr);
                //添加借书请求
                bookService.addBorrow(bookid,reader.getId());
                resp.sendRedirect("/book?method=findAllBorrow&page=1");
                break;
            case "findAllBorrow":
                pageStr = req.getParameter("page");
                page = Integer.parseInt(pageStr);
                //展示当前用户的所有借书记录
                List<Borrow> borrowList = null;
                try {
                    borrowList = bookService.findAllBorrowByReaderId(reader.getId(),page);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                req.setAttribute("list", borrowList);
                req.setAttribute("dataPrePage",6);
                req.setAttribute("currentPage",page);
                req.setAttribute("pages", bookService.getBorrowPages(reader.getId()));
                req.getRequestDispatcher("borrow.jsp").forward(req, resp);

        }

    }
}
