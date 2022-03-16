package com.itmk.web.menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.menu.entity.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {
    //根据用户ID查询对应的权限
    List<Menu> getMenuListByUserId( Long userId);
}
