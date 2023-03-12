package com.teligen.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teligen.entity.Admin;
import com.teligen.entity.AdminDTO;
import com.teligen.service.AdminService;
import com.teligen.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * (Admin)表控制层
 *
 * @author makejava
 * @since 2023-03-12 22:28:23
 */
@RestController
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RedisTemplate redisTemplate;


    @PostMapping("/tokens")
    public Map<String,String>tokens(@RequestBody Admin admin, HttpSession session){
        Map<String,String>result=new HashMap<>();
        log.info("接收到admin对象为:{}", JacksonUtils.writeValue(admin));
        // 登录
        Admin adminDB = adminService.login(admin);
        String token=session.getId();

        //登陆成功，存token
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set(token,adminDB,1, TimeUnit.HOURS);

        result.put("token",token);
        return result;
    }

    @GetMapping("/admin-user")
    public AdminDTO admin(String token){
        log.info("当前token信息为:{}",token);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        Admin admin =(Admin) redisTemplate.opsForValue().get(token);
        AdminDTO adminDTO=new AdminDTO();
        BeanUtils.copyProperties(admin,adminDTO);
        return adminDTO;
    }


    /**
     *
     * @param token 需要登出的用户的token数据
     */
    @DeleteMapping("/tokens/{token}")
    public void logOut(@PathVariable("token")String token){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.delete(token);
    }
}

