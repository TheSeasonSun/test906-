package com.itmk.web.user.controller;

import com.itmk.utils.ResultVo;
import com.itmk.web.user.entity.User;
import com.itmk.web.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getAllUser")
    public ResultVo getAllUser(){
        List<User> list = userService.list();
        return new ResultVo("成功", 200, list);
    }
}
