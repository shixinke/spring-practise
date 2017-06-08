package com.shixinke.springworld.service;

import com.shixinke.springworld.mapper.UserMapper;
import com.shixinke.springworld.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

public interface UserService {
    List<User> userList();
    User userInfo(int userId);
    int addUser(User user);
    int updateUser(User user);
    int deleteUser(int userId);
}

