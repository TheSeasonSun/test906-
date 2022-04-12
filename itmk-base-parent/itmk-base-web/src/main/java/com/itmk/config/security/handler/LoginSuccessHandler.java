package com.itmk.config.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.itmk.config.jwt.JwtUtils;
import com.itmk.web.user.entity.User;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//认证成功处理器，主要作用是返回json格式的数据
@Component("loginSuccessHandler")
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //1.生成token
        User user = (User)authentication.getPrincipal();
        String token = jwtUtils.generateToken(user);
        //该token过期的时间，返回给前端
        long expireTime = Jwts.parser()   //得到DefaultJwtParser
                .setSigningKey(jwtUtils.getSecret())  //设置签名的秘钥
                .parseClaimsJws(token.replace("jwt_", ""))
                .getBody().getExpiration().getTime();
        LoginResult vo = new LoginResult();
        vo.setId(user.getId());
        vo.setCode(200);
        vo.setToken(token);
        vo.setExpireTime(expireTime);
        //返回JSON
        //SerializerFeature.DisableCircularReferenceDetect消除循环引用
        String res = JSONObject.toJSONString(vo, SerializerFeature.DisableCircularReferenceDetect);
        //返回格式
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = httpServletResponse.getOutputStream();
        out.write(res.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
