package com.shixinke.springworld.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shixinke.springworld.dao.UserDao;
import com.shixinke.springworld.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDao user;

    @Autowired
    private User userModel;

    @GetMapping("/index")
    public ModelAndView index() {
        List<User> userList = user.userList();

        ModelAndView mv = new ModelAndView("user/index");
        mv.addObject("title", "用户管理");
        mv.addObject("userList", userList);
        return mv;
    }

    @GetMapping("/add")
    public ModelAndView add() {
        ModelAndView mv = new ModelAndView("user/add");
        mv.addObject("title", "添加用户");
        return mv;
    }

    @ResponseBody
    @PostMapping("/add")
    public Map<String,Object> save(HttpServletRequest request) {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String nickname = request.getParameter("nickname");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        byte status = Byte.parseByte(request.getParameter("status"));
        Map<String, Object> response = new HashMap<String, Object>();
        if (account == null || account.length() <= 0) {
            response.put("code", 50001);
            response.put("message", "账号不能为空");
            return response;
        }
        if (password == null || password.length() <= 0) {
            response.put("code", 50002);
            response.put("message", "密码不能为空");
            return response;
        }
        if (nickname == null || nickname.length() <= 0) {
            response.put("code", 50003);
            response.put("message", "昵称不能为空");
            return response;
        }
        if (email == null || email.length() <= 0) {
            response.put("code", 50004);
            response.put("message", "邮箱不能为空");
            return response;
        }
        if (mobile == null || mobile.length() <= 0) {
            response.put("code", 50001);
            response.put("message", "手机号不能为空");
            return response;
        }
        userModel.setAccount(account);
        userModel.setPassword(password);
        userModel.setNickname(nickname);
        userModel.setEmail(email);
        userModel.setMobile(mobile);
        userModel.setStatus(status);
        int result = user.addUser(userModel);

        if (result > 0) {
            response.put("code", 200);
            response.put("message", "操作成功");
        } else {
            response.put("code", 50001);
            response.put("message", "操作失败");
        }

        return response;
    }

    @ResponseBody
    @GetMapping("/edit")
    public ModelAndView edit(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("id"));
        String error = "";
        if (userId < 1) {
            error = "非法用户";
        } else {
            userModel = user.detail(userId);
        }
        ModelAndView mv = new ModelAndView("user/edit");
        mv.addObject("title", "编辑用户信息");
        mv.addObject("userInfo", userModel);
        mv.addObject("error", error);
        return mv;
    }

    @ResponseBody
    @PostMapping("/edit")
    public Map<String,Object> update(HttpServletRequest request) {
        String account = request.getParameter("account");
        String nickname = request.getParameter("nickname");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        byte status = Byte.parseByte(request.getParameter("status"));
        int userId = Integer.parseInt(request.getParameter("user_id"));
        Map<String, Object> response = new HashMap<String, Object>();
        if (account == null || account.length() <= 0) {
            response.put("code", 50001);
            response.put("message", "账号不能为空");
            return response;
        }
        if (userId <= 0) {
            response.put("code", 50002);
            response.put("message", "非法用户");
            return response;
        }
        if (nickname == null || nickname.length() <= 0) {
            response.put("code", 50003);
            response.put("message", "昵称不能为空");
            return response;
        }
        if (email == null || email.length() <= 0) {
            response.put("code", 50004);
            response.put("message", "邮箱不能为空");
            return response;
        }
        if (mobile == null || mobile.length() <= 0) {
            response.put("code", 50001);
            response.put("message", "手机号不能为空");
            return response;
        }
        userModel.setAccount(account);
        userModel.setNickname(nickname);
        userModel.setEmail(email);
        userModel.setMobile(mobile);
        userModel.setStatus(status);
        userModel.setUserId(userId);
        int result = user.updateUser(userModel);

        if (result > 0) {
            response.put("code", 200);
            response.put("message", "修改成功");
        } else {
            response.put("code", 50001);
            response.put("message", "修改失败");
        }

        return response;
    }

    @ResponseBody
    @PostMapping("/delete")
    public Map<String,Object> delete(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("id"));
        int result = user.deleteUser(userId);
        Map<String, Object> response = new HashMap<String, Object>();
        if (result > 0) {
            response.put("code", 200);
            response.put("message", "删除成功");
        } else {
            response.put("code", 50001);
            response.put("message", "删除失败");
        }
        return response;
    }
}
