package com.myself.service;

import com.myself.entity.Book;
import com.myself.entity.Borrow;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public interface BookService {
    public List<Book> findAll(int page);
    public int getPages();
    public int getBorrowPages(Integer readerid);
    public void addBorrow(Integer bookid, Integer readerid);
    public List<Borrow> findAllBorrowByReaderId(Integer id,Integer page) throws SQLException;
    public List<Borrow> findAllBorrowByState(Integer state,Integer page);
    public int getBorrowPagesByState(Integer state);
    public void handleBorrow(Integer borrowId, Integer state, Integer adminId);

}

