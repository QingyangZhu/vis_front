package com.vis.accountingvis.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vis.accountingvis.dto.UserResponseDTO;
import com.vis.accountingvis.entity.User;
import com.vis.accountingvis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public boolean register(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User existingUser = userMapper.selectOne(queryWrapper);
        if (existingUser != null) {
            return false; // 用户名已存在
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // 注意：密码应加密存储
        userMapper.insert(user);
        return true;
    }

    public boolean login(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) return false;
        // 注意：密码比较应使用加密后的密码进行匹配
        return user.getPassword().equals(password);
    }

    // 新增方法实现
    public List<UserResponseDTO> getAllUsersForAdmin() {
        List<User> users = userMapper.selectList(null);
        return users.stream()
                .map(user -> new UserResponseDTO(user.getUid(), user.getUsername()))
                .collect(Collectors.toList());
    }

    // 新增方法实现
    public boolean deleteUserForAdmin(Integer uid) {
        return userMapper.deleteById(uid) > 0;
    }
}
