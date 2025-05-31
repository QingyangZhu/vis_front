package com.vis.accountingvis.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vis.accountingvis.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
