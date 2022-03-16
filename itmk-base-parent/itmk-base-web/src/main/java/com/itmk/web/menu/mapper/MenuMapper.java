package com.itmk.web.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.web.menu.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    //根据用户ID查询对应的权限
    List<Menu> getMenuListByUserId(@Param("userId") Long userId);
}
