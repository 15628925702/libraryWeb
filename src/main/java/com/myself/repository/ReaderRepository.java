package com.myself.repository;

import com.myself.entity.Reader;

public interface ReaderRepository {
    public Reader login(String username,String password);
}
