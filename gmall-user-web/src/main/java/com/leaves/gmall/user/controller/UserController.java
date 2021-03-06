package com.leaves.gmall.user.controller;


import com.alibaba.dubbo.config.annotation.Reference;

import com.leaves.gmall.model.UmsMember;
import com.leaves.gmall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author Chenweiwei
 * @Date 2021/1/6 15:04
 * @Version 1.0
 */

@Controller
public class UserController {

    @Reference
    UserService userService;


    @RequestMapping("index")
    @ResponseBody
    public String index() {
        return "hello user";
    }

    @RequestMapping("getAllUser")
    @ResponseBody
    public List<UmsMember> getAllUser() {
        List<UmsMember> UmsMembers = userService.getAllUser();
        return UmsMembers;
    }
}
