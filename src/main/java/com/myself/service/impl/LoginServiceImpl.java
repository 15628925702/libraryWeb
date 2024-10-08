package com.myself.service.impl;

import com.myself.repository.AdminRepository;
import com.myself.repository.ReaderRepository;
import com.myself.repository.impl.AdminRepositoryImpl;
import com.myself.repository.impl.ReaderRepositoryImpl;
import com.myself.service.LoginService;

public class LoginServiceImpl implements LoginService {

    private ReaderRepository readerRepository = new ReaderRepositoryImpl();
    private AdminRepository adminRepository = new AdminRepositoryImpl();

    @Override
    public Object login(String username, String password, String type) {
        Object object = null;
        switch (type) {
            case "reader":
                object= readerRepository.login(username, password);
                break;

            case "admin":
                object= adminRepository.login(username, password);
                break;
        }
        return object;
    }
}
