package com.shixinke.springworld.service.impl;

import com.shixinke.springworld.mapper.UserMapper;
import com.shixinke.springworld.model.User;
import com.shixinke.springworld.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    public List<User> userList() {
        return userMapper.userList();
    }

    public User userInfo(int userId) {
        return userMapper.userInfo(userId);
    }

    public int addUser(User user) {
        String salt = randString(4);
        Date date = new Date();
        user.setSalt(salt);
        user.setPassword(encodePassword(user.getPassword(), salt));
        user.setUpdateTime(date);
        user.setLastLoginTime(date);
        user.setCreateTime(date);
        return userMapper.addUser(user);
    }

    public int updateUser(User user) {
        Date date = new Date();
        user.setUpdateTime(date);
        return userMapper.updateUser(user);
    }

    public int deleteUser(int userId) {
        return userMapper.deleteUser(userId);
    }

    public static String encodePassword(String password, String salt) {
        String newStr = password + salt;
        try {
            return md5(newStr);
        } catch(Exception e) {
            return newStr;
        }
    }

    public static String randString(int length) {
        String strRange = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLNMOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(strRange.length());
            buf.append(strRange.charAt(number));
        }
        return buf.toString();
    }

    public static String md5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr = base64en.encode(md.digest(str.getBytes("utf-8")));
        return newstr;
    }

}
