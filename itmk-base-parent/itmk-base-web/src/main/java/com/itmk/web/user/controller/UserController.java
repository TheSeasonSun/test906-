package com.itmk.web.user.controller;

import com.itmk.redis.RedisService;
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
    @Autowired
    private RedisService redisService;

    @GetMapping("/getAllUser")
    public ResultVo getAllUser(){
        List<User> list = userService.list();
        //存
        redisService.set("name", "张三", 60L);
        //取
        String name = redisService.get("name");

        return new ResultVo("成功", 200, list);
    }
}
