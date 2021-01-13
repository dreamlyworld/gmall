package com.leaves.gmall.user.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.leaves.gmall.model.UmsMember;
import com.leaves.gmall.service.UserService;
import com.leaves.gmall.user.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author Chenweiwei
 * @Date 2021/1/6 15:07
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<UmsMember> getAllUser() {

        List<UmsMember>  umsMemberList =userMapper.selectAll();
        return umsMemberList;
    }
}
