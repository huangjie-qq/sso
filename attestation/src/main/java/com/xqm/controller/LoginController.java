package com.xqm.controller;

import com.alibaba.fastjson.JSON;
import com.xqm.pojo.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author：球某
 * @Date：2021/10/30/23:18
 */
@Controller
public class LoginController {

    @Resource
    private RedisTemplate redisTemplate;


    /**
     * 处理登录请求
     * @param user
     * @param response
     * @return
     */
    @PostMapping("doLogin")
    public String dolgoin(User user, HttpServletResponse response){
        String token= UUID.randomUUID().toString().replace("-","");
        redisTemplate.opsForValue().set(token,JSON.toJSONString(user),30, TimeUnit.MINUTES);
        Cookie cookie = new Cookie("sso_token", token);
        response.addCookie(cookie);
        return "redirect:"+user.getUrl()+"?token="+token;
    }

    /**
     * 跳转到登录视图
     * @param url
     * @param model
     * @param token
     * @return
     */
    @GetMapping("login.html")
    public String loginPage(@RequestParam("redirect_ur1") String url, Model model,
                            @CookieValue(value = "sso_token",required = false) String token){
        if(!StringUtils.isEmpty(token)){
            return "redirect:"+url+"?token="+token;
        }
        model.addAttribute("url",url);
        return "login";
    }

}



