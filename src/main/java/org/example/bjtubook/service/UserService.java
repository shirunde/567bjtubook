package org.example.bjtubook.service;

import org.example.bjtubook.entity.User;
import java.util.List;

public interface UserService {
    User register(User user);
    User login(String username, String password);
    User getById(Integer id);
    User update(User user);
    List<User> getAllUsers();
    User updateBanStatus(Integer userId, Integer banStatus);
}
