package com.li.schoolGo.controller;

import com.li.schoolGo.bean.ResponseBean;
import com.li.schoolGo.bean.SysUser;
import com.li.schoolGo.Constant.TokenConst;
import com.li.schoolGo.service.SysUserService;
import com.li.schoolGo.util.CookieUtil;
import com.li.schoolGo.util.IsAllow;
import com.li.schoolGo.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private SysUserService sysUserService;

    @Value("token.key")
    String key;

    @IsAllow
    @RequestMapping({"toLogin"})
    public String toLogin(){
        return "login";
    }

    /**
     * 用户登录验证
     * @param user springMVC可以自动封装参数为一个bean对象
     * @return
     */
    @PostMapping("doLogin")
    @IsAllow
    @ResponseBody
    public ResponseBean doLogin(SysUser user, HttpServletRequest request, HttpServletResponse response){

        logger.debug("传入的表单值是:" + user);

        SysUser sysUser = sysUserService.getSysUser(user);

        if(sysUser != null){
            if(sysUser.getStatus() == 0){
                return ResponseBean.baseError("该用户已封禁，请联系管理员");
            }

            Map<String,Object> map = new HashMap<>();
            map.put("userId",sysUser.getId());
            map.put("nickName",sysUser.getLoginName());
            String token = JwtUtil.encode(key, map);

            CookieUtil.setCookie(request,response, TokenConst.TOKEN_NAME, token, TokenConst.MAX_COOKIE_AGE, false);

            ResponseBean responseBean = ResponseBean.baseSuccess("登录成功");
            logger.debug("用户:" + sysUser.getLoginName() + "  登录，时间是" + new SimpleDateFormat().format(new Date()));
            return responseBean;
        }
        return ResponseBean.baseError("用户名或密码错误");
    }

    /**
     * 登出方法
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("logout")
    @IsAllow
    public String logout(HttpServletRequest request, HttpServletResponse response){
        //新建一个Cookie，设置值为null，最大存活时间是0，对项目所有路径下有效；目的是为了清除Cookie值
        Cookie cookie = new Cookie(TokenConst.TOKEN_NAME,"");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        //清除Cookie后跳转登录页面
        return "login";
    }

}
