package com.leaves.gmall.manage.dao;

import com.leaves.gmall.model.PmsBaseAttrInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author Chenweiwei
 * @Date 2021/1/22 11:43
 * @Version 1.0
 */
public interface PmsBaseAttrInfoMapper extends Mapper<PmsBaseAttrInfo> {
    List<PmsBaseAttrInfo> selecttListByValueIds(@Param("valueIdStr") String valueIdStr);
}
