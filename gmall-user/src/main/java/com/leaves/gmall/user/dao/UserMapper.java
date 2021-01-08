package com.leaves.gmall.user.dao;

import com.leaves.gmall.user.model.UmsMember;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author Chenweiwei
 * @Date 2021/1/6 15:10
 * @Version 1.0
 */
public interface UserMapper extends Mapper<UmsMember> {
    List<UmsMember> selectAllUser();
}
