package com.itmk.config.security.filter;

import com.itmk.config.jwt.JwtUtils;
import com.itmk.config.security.detailservice.CustomerUserDetailsService;
import com.itmk.config.security.exception.CustomerAuthenionException;
import com.itmk.config.security.handler.LoginFailureHandler;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Data
@Component("checkTokenFilter")
public class CheckTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    private LoginFailureHandler loginFailureHandler;
    @Value("${itmk.loginUrl}")
    private String loginUrl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            //token验证，登录请求不做token验证
            //获取请求url
            String url = request.getRequestURI();
            if (!url.equals(loginUrl)){
                validateToken(request);
            }

        }catch (AuthenticationException e){
            loginFailureHandler.onAuthenticationFailure(request,response,e);
            return;
        }

        //token验证，登录请求不做token验证
        filterChain.doFilter(request,response);

    }

    //token验证
    private void validateToken(HttpServletRequest request){
        //从头部获取token
        String token = request.getHeader("token");
        //从头部获取token失败，那么从参数中获取
        if(StringUtils.isEmpty(token)){
            token = request.getParameter("token");

        }
        //没有获取到token
        if(StringUtils.isEmpty(token)){
            throw new CustomerAuthenionException("token获取失败");
        }
        //解析token
        String username = jwtUtils.getUsernameFromToken(token);
        if(StringUtils.isEmpty(username)){
            throw new CustomerAuthenionException("token解析失败");
        }
        //获取用户信息
        UserDetails details = customerUserDetailsService.loadUserByUsername(username);
        if(details == null){
            throw new CustomerAuthenionException("token解析失败");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(details,null,details.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //设置到spring security上下文
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
