package com.myself.repository;

import com.myself.entity.Book;
import com.myself.entity.Borrow;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.sql.SQLException;
import java.util.List;

public interface BorrowRepository {
    public void insert(Integer bookid, Integer readerid, String borrowtime, String returntime, Integer adminid, Integer state);
    public List<Borrow> findAllByReaderId(Integer id,Integer index,Integer limit) throws SQLException;
    public List<Borrow> findAllByState(Integer state,Integer index,Integer limit);
    public int count(Integer readerid);
    public int countByState(Integer state);

    public void handle(Integer borrowId, Integer state, Integer adminId);
}
