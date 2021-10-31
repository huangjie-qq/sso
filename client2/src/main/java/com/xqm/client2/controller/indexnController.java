package com.xqm.client2.controller;

import com.alibaba.fastjson.JSON;
import com.xqm.client2.pojo.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @Author：球某
 * @Date：2021/10/31/0:03
 */
@Controller
public class indexnController {


    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping("list")
    public String list(@RequestParam(value = "token",required = false) String token, HttpSession session){
        if(!StringUtils.isEmpty(token)){
            String json = (String) redisTemplate.opsForValue().get(token);
            User loginUser = JSON.parseObject(json, User.class);
            if(loginUser!=null)
                session.setAttribute("loginUser",loginUser);
        }
        if(session.getAttribute("loginUser")==null)
            return "redirect:http://attestation.com:8000/login.html?redirect_ur1=http://client2:8200/list";
        return "list";
    }

}
