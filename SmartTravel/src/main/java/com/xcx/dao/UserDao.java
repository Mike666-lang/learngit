package com.xcx.dao;


import com.xcx.entity.User;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UserDao {

    int addUser(User user);

    Map<String, Object> finStudentByUserName(String name) throws SQLException;

    List<User> findAllUser() throws SQLException;

    int delUser(String id) throws SQLException;

    Map<String, Object> findUserById(Integer id) throws SQLException;

    int updateUser(User user) throws SQLException;

    List<Map<String,Object>> searchUser(String field,String keyword) throws SQLException;

}
