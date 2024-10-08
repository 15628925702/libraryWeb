package com.myself.repository;
import com.myself.entity.Admin;

public interface AdminRepository {
    public Admin login(String username,String password);
}
