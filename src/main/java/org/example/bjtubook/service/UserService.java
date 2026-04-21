package org.example.bjtubook.service;

import org.example.bjtubook.entity.User;

public interface UserService {
    User register(User user);
    User login(String username, String password);
    User getById(Integer id);
    User update(User user);
}
