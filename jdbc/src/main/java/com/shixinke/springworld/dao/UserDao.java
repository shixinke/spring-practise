package com.shixinke.springworld.dao;

import com.shixinke.springworld.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by shixinke on 17-6-2.
 */

public class UserDao {
    private JdbcTemplate jdbcTemplate;
    private User user;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setUser(User user) {
        this.user = user;
    }

    public List<User> userList(){
        String sql = "SELECT * FROM lg_user";
        List userList =  jdbcTemplate.query(sql, new UserMapper());
        return userList;
    }

    public int addUser(User user) {
        user.setSalt(randString(4));
        user.setPassword(encodePassword(user.getPassword(), user.getSalt()));
        String sql = "INSERT INTO lg_user(account, password, salt, nickname, email, mobile, last_login_time, status, create_time, update_time) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object args[] = new Object[]{user.getAccount(), user.getPassword(), user.getSalt(), user.getNickname(), user.getEmail(), user.getMobile(), currentDateTime(), user.getStatus(), currentDateTime(), currentDateTime()};
        int types[] = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.TINYINT, Types.TIMESTAMP, Types.TIMESTAMP};
        return jdbcTemplate.update(sql, args, types);
    }

    public int updateUser(User user) {
        String sql = "UPDATE lg_user SET account = ?, nickname = ?, email = ?, mobile = ?, status = ?, update_time = ? WHERE user_id = ?";
        Object args[] = new Object[]{user.getAccount(), user.getNickname(), user.getEmail(), user.getMobile(), user.getStatus(), currentDateTime(), user.getUserId()};
        int types[] = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TINYINT, Types.DATE, Types.INTEGER};
        for (int i=0; i<args.length; i++) {
            System.out.println(args[i]);
            System.out.println(types[i]);
        }
        return jdbcTemplate.update(sql, args, types);
    }

    public User detail(int userId) {
        String sql = "SELECT * FROM lg_user WHERE user_id = ?";
        Object args[] = new Object[]{userId};
        Object userInfo =  jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper(User.class));
        return (User) userInfo;
    }

    public int deleteUser(int userId) {
        String sql = "DELETE FROM lg_user WHERE user_id = ?";
        Object args[] = new Object[]{userId};
        int types[] = new int[]{Types.INTEGER};
        return jdbcTemplate.update(sql, args, types);
    }

    public static String encodePassword(String password, String salt) {
        String newStr = password + salt;
        try {
            return md5(newStr);
        } catch(Exception e) {
            return newStr;
        }
    }

    public static String currentDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
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



    protected class UserMapper implements RowMapper {
        public Object mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(resultSet.getInt("user_id"));
            user.setAccount(resultSet.getString("account"));
            user.setPassword(resultSet.getString("password"));
            user.setNickname(resultSet.getString("nickname"));
            user.setEmail(resultSet.getString("email"));
            user.setMobile(resultSet.getString("mobile"));
            user.setStatus(resultSet.getByte("status"));
            user.setCreateTime(resultSet.getDate("create_time"));
            user.setLastLoginTime(resultSet.getDate("last_login_time"));
            user.setLastLoginIp(resultSet.getString("last_login_ip"));
            user.setSalt(resultSet.getString("salt"));
            user.setUpdateTime(resultSet.getDate("update_time"));
            return user;
        }
    }
}
