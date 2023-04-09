package com.xcx.service.impl;

import com.xcx.dao.UserDao;
import com.xcx.dao.impl.UserDaoImpl;
import com.xcx.entity.User;
import com.xcx.service.UserService;
import org.apache.commons.beanutils.BeanUtils;

import javax.rmi.CORBA.Stub;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();
    boolean flag = false;

    @Override
    public Map<String, Object> findUserByName(String name) {
        Map<String, Object> user = null;

        try {
            user = userDao.finStudentByUserName(name);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return user;
    }

    @Override
    public boolean addUser(Map<String, String[]> map) {
        User user = new User();

        try {
            BeanUtils.populate(user, map);
            flag = userDao.addUser(user) != 0;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<User> findAllUser() {
        List<User> allUser = null;

        try {
            allUser = userDao.findAllUser();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allUser;
    }

    @Override
    public boolean delUser(String id) {
        boolean flag1 = false;
        try {
            flag1 = userDao.delUser(id) != 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flag1;
    }

    @Override
    public Map<String, Object> findUserById(Integer id) {
        Map<String, Object> map = null;
        try {
            map = userDao.findUserById(id);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return map;
    }

    @Override
    public boolean updateUser(Map<String, String[]> map) {
        User user = new User();

        try {
            BeanUtils.populate(user, map);
            try {
                flag = userDao.updateUser(user) != 0;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Map<String,Object>>  searchUser(String feild,String keyword) {
        // 1, 创建指定的容器
        List<Map<String,Object>> list = null;

        try {
            // 2. 获取的结果进行传入操作
            list = userDao.searchUser(feild,keyword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 返回指定的信息数据
        return list;
    }
}
