package com.itmk.config.security.detailservice;

import com.itmk.web.menu.entity.Menu;
import com.itmk.web.menu.service.MenuService;
import com.itmk.web.user.entity.User;
import com.itmk.web.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("customerUserDetailsService")
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //认证
        User user = userService.getUserByUsername(s);
        if (user == null){
            throw new UsernameNotFoundException("用户名或密码错误！");
        }
        //授权
        List<Menu> menuList = menuService.getMenuListByUserId(user.getId());
        //去掉null
        List<String> collect = menuList.stream().filter(item -> item != null).map(item -> item.getCode()).filter(item -> item != null).collect(Collectors.toList());
        String[] strings = collect.toArray(new String[collect.size()]);
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(strings);
        user.setAuthorities(authorityList);
        user.setPermissionList(menuList);
        return user;
    }
}
