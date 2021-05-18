package com.li.schoolGo.interceptor;

import com.alibaba.fastjson.JSON;
import com.li.schoolGo.bean.SysUser;
import com.li.schoolGo.constant.TokenConst;
import com.li.schoolGo.service.SysUserService;
import com.li.schoolGo.util.CookieUtil;
import com.li.schoolGo.util.IsAllow;
import com.li.schoolGo.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class MyInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

    @Value("token.key")
    private String key;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("拦截器拦截-------访问地址" + request.getRequestURI());

        //处理器强转
        HandlerMethod handlerMethod = (HandlerMethod)handler;

        //获取Controller方法注解：@isAllow
        IsAllow isAllow = handlerMethod.getMethodAnnotation(IsAllow.class);

        //标有此注解的方法一律放行
        if(isAllow != null){
            return true;
        }

        //从cookie中获取用户token值
        String token = CookieUtil.getCookieValue(request, TokenConst.TOKEN_NAME,false);

        //如果获取到的token是null，重定向到登录页
        if(token == null || token.length() < 1){
            String url = "toLogin";
            response.sendRedirect(url);
            return false;
        }

        //token存在, 解密token, 验证用户是否正确
        Map<String, Object> userMap = JwtUtil.decode(token, key);
        String userId = (String) userMap.get("userId");

        String result = sysUserService.verify(userId);

        if(!StringUtils.isEmpty(result)){
            SysUser user = (SysUser) JSON.parseObject(result,SysUser.class);
            request.setAttribute("nickName", user.getUserName());
            request.setAttribute("headImg",user.getHeadImg());
            request.setAttribute("userId",user.getId());
            request.setAttribute("areaId",String.valueOf(user.getUserAreaId()));
            if(user.getIsSuperManager() == 1){
                request.setAttribute("isSuperManager",true);
            }
            return true;
        }else {
            response.sendRedirect("toLogin");
            return false;
        }
    }
}
