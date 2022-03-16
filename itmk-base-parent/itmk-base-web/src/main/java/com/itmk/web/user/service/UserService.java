package com.itmk.web.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.user.entity.User;

public interface UserService extends IService<User> {
    //Spring security认证时调用
    User getUserByUsername(String username);
}
