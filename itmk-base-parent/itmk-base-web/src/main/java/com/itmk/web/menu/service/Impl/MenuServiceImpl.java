package com.itmk.web.menu.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.menu.entity.Menu;
import com.itmk.web.menu.mapper.MenuMapper;
import com.itmk.web.menu.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<Menu> getMenuListByUserId(Long userId) {
        return this.baseMapper.getMenuListByUserId(userId);
    }
}
