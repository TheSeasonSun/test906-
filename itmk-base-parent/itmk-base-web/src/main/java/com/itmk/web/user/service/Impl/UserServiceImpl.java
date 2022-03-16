package com.itmk.web.user.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.user.entity.User;
import com.itmk.web.user.mapper.UserMapper;
import com.itmk.web.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Override
    public User getUserByUsername(String username) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.lambda().eq(User::getUsername, username);
        return this.baseMapper.selectOne(query);
    }
}
