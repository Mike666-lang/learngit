package com.xcx.dao.impl;

import com.xcx.dao.UserDao;
import com.xcx.entity.User;
import util.BaseDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UserDaoImpl extends BaseDao implements UserDao {
    @Override
    public int addUser(User user) {
        String sql = "insert into traffic.user( username, password, usertype, sex, address, garage_num, etc_num) values (?, ?, ?, ?, ?, ?, ?)";
        return super.update(sql, user.getUsername(), user.getPassword(), user.getUsertype(), user.getSex(), user.getAddress(), user.getGarage_num(),user.getEtc_num());
    }

    @Override
    public Map<String, Object> finStudentByUserName(String username) throws SQLException {
        String sql = "select * from traffic.user where username = ?";
        return super.queryMap(sql, username);
    }

    @Override
    public List<User> findAllUser() throws SQLException {
        String sql = "select * from traffic.user";
        return super.queryBeanList(sql, User.class,null);
    }

    @Override
    public int delUser(String id) throws SQLException {
        String sql = "delete from user where id = ?";
        return super.update(sql, id);
    }

    @Override
    public Map<String, Object> findUserById(Integer id) throws SQLException {
        String sql = "select * from user where id = ?";
        return super.queryMap(sql, id);
    }

    @Override
    public int updateUser(User user) throws SQLException {
        String sql = "update user set username = ?, password = ?, sex = ?, address = ?, garage_num = ?, etc_num = ? where id = ?";
        return super.update(sql, user.getUsername(), user.getPassword(),user.getSex(), user.getAddress(), user.getGarage_num(), user.getEtc_num(), user.getId());
    }

    @Override
    public List<Map<String,Object>> searchUser(String field,String keyword) throws SQLException {
        //String sql = "select id, username, password, usertype, sex, address, garage_num, etc_num from user where username like ?";
        String sql = "select * from user";
        if (field != null && !"".equals(field)&&keyword != null && !"".equals(keyword)){
            sql += " where "+field+" like '%"+keyword+"%' ";
        }
        System.out.println("_-_----------------------------------------------");
        System.out.println(queryMapList(sql, field, keyword));
        System.out.println("_-_----------------------------------------------");
        return super.queryMapList(sql, field, keyword);
    }
}
