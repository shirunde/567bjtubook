package org.example.bjtubook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.bjtubook.entity.User;
import org.example.bjtubook.mapper.UserMapper;
import org.example.bjtubook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User register(User user) {
        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 设置默认角色
        user.setRole(0);
        // 保存用户
        userMapper.insert(user);
        return user;
    }

    @Override
    public User login(String username, String password) {
        // 根据用户名查询用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = userMapper.selectOne(wrapper);
        
        // 验证密码
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public User getById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public User update(User user) {
        // 如果更新密码，需要加密
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userMapper.updateById(user);
        return user;
    }
}
