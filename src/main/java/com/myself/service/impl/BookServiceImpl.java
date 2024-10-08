package com.myself.service.impl;

import com.myself.entity.Book;
import com.myself.entity.Borrow;
import com.myself.repository.BookRepository;
import com.myself.repository.BorrowRepository;
import com.myself.repository.impl.BookRepositoryImpl;
import com.myself.repository.impl.BorrowRepositoryImpl;
import com.myself.service.BookService;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookServiceImpl implements BookService {

    private BookRepository bookRepository = new BookRepositoryImpl();
    private BorrowRepository borrowRepository = new BorrowRepositoryImpl();
    private final int LIMIT = 6;


    @Override
    public List<Book> findAll(int page) {
        int index=(page-1)*LIMIT;
        return bookRepository.findAll(index,LIMIT);
    }

    @Override
    public int getPages() {
        int count = bookRepository.count();
        int page = 0;
        page = (int) Math.ceil((double) count / LIMIT);
        return page;
    }


    @Override
    public int getBorrowPages(Integer readerid) {
        int count = borrowRepository.count(readerid);
        int page = 0;
        page = (int) Math.ceil((double) count / LIMIT);
        return page;
    }

    @Override
    public void addBorrow(Integer bookid, Integer readerid) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String borrowTime = simpleDateFormat.format(date);
        //还书时间，借书时间+14天
        Calendar calendar = Calendar.getInstance();
        int dates = calendar.get(Calendar.DAY_OF_YEAR) + 14;
        calendar.set(Calendar.DAY_OF_YEAR, dates);
        Date date2 = calendar.getTime();
        String returnTime = simpleDateFormat.format(date2);
        borrowRepository.insert(bookid,readerid,borrowTime,returnTime,null,0);

    }

    @Override
    public List<Borrow> findAllBorrowByReaderId(Integer id,Integer page) throws SQLException {
        //业务：将page换算成index、limit
        int index = (page - 1) * LIMIT;
        return borrowRepository.findAllByReaderId(id,index,LIMIT);
    }

    @Override
    public List<Borrow> findAllBorrowByState(Integer state,Integer page) {
        int index = (page - 1) * LIMIT;
        return borrowRepository.findAllByState(state,index,LIMIT);
    }

    @Override
    public int getBorrowPagesByState(Integer state) {
        int count = borrowRepository.countByState(state);
        int page = 0;
        page = (int) Math.ceil((double) count / LIMIT);
        return page;
    }

    @Override
    public void handleBorrow(Integer borrowId, Integer state, Integer adminId) {
        borrowRepository.handle(borrowId,state,adminId);
    }
}
