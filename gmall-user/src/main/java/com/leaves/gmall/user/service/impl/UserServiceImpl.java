package com.leaves.gmall.user.service.impl;

import com.leaves.gmall.user.dao.UserMapper;
import com.leaves.gmall.user.model.UmsMember;
import com.leaves.gmall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
