package com.xcx.service;

import com.xcx.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String, Object> findUserByName(String name);

    boolean addUser(Map<String, String[]> map);

    List<User> findAllUser();

    boolean delUser(String id);

    Map<String, Object> findUserById(Integer id);

    boolean updateUser(Map<String, String[]> map);

    List<Map<String,Object>> searchUser(String field,String keyword);

}
