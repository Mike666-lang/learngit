package util.impl;

import org.apache.commons.beanutils.BeanUtils;
import util.ResultSetHandler;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anonymous 2023/3/15 15:46
 */
public class BeanListHandler<T> implements ResultSetHandler<List<T>> {

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
    public BeanListHandler(Class<T> cls) {
        this.cls = cls;
    }

    @Override
    public List<T> handle(ResultSet res) throws SQLException {
        // 1. 准备 List 集合
        List<T> list = new ArrayList<>();

        // 2. 获取结果集元数据
        ResultSetMetaData metaData = res.getMetaData();

        // 3. 获取结果集中字段个数
        int columnCount = metaData.getColumnCount();

        // 4. 定义 T 类型的变量
        T t = null;

        while (res.next()) {
            /*
             5. 实例化对象
             【大问题】
                1. 当前方法中存在泛型，泛型对应的具体数据类型尚未约束
                2. 实例化对象操作
                    既没有具体类型，又没有 Class 对象。
                3. 方法还是遵从接口实现的方法，无法修改方法参数。

             【解决思路】
                1. 方法可以使用的数据，第一类是方法的参数，第二类是类内的成员变量
                当前方法无法修改参数，只能依赖于【成员变量】

                2. 对于当前变量/数据的需求是可以进行反射操作实例化对象的 Class 类型。
                成员变量数据类型 Class

                3. 成员变量不可能是固定的数据，无法满足数据类型的多样性，需要用户来
                明确约束当前成员变量数据情况，可以通过【构造方法来完成】

                4. 泛型对应的具体数据类型，同样可以使用 Class 类型来进行明确约束

                5. 当前功能的实现是【必须】要求用户提供 Class 数据，约束泛型的同时，
                提供反射操作的基本条件
             */
            try {
                // 实例化对象
                t = cls.getConstructor().newInstance();

                for (int i = 1; i <= columnCount; i++) {
                    BeanUtils.setProperty(t, metaData.getColumnName(i), res.getObject(i));
                }

                list.add(t);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return list.isEmpty() ? null : list;
    }
}










