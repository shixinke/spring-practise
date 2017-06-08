package com.shixinke.springworld.mapper;

import com.shixinke.springworld.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userMapper")
public interface UserMapper {
    List<User> userList();

    User userInfo(int userId);

    int addUser(User user);

    int updateUser(User user);

    int deleteUser(int userId);
}
