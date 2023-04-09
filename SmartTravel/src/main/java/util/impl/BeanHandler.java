package util.impl;

import util.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Anonymous 2023/3/15 16:21
 */
public class BeanHandler<T> implements ResultSetHandler<T> {

    /**
     * 1. 成员变量
     * 2. final 修饰要求强制初始化，通过构造方法来进行初始化操作，提高自由度
     * 3. Class 类型，提供反射的万恶之源
     * 4. 泛型使用，约束当前 List 集合中存储的泛型对应具体数据类型
     */
    private final Class<T> cls;

    /**
     * 构造方法，参数是 Class 类型，并且带有泛型，约束当前类名声明泛型对应
     * 具体数据类型，同时提供反射基本数据要求。
     * 也限制了接口中 List 集合存储泛型对应具体数据类型
     *
     * @param cls Class 类型，同时约束泛型
     */
    public BeanHandler(Class<T> cls) {
        this.cls = cls;
    }

    @Override
    public T handle(ResultSet res) throws SQLException {
        List<T> list = new BeanListHandler<>(cls).handle(res);

        return list != null ? list.get(0) : null;
    }
}
