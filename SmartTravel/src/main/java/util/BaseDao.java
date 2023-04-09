package util;

import util.impl.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Anonymous 2023/3/14 17:11
 */
public abstract class BaseDao {
    /**
     * 通过 更新方法，支持 insert delete update 操作
     *
     * @param sql        目标执行的 SQL 语句
     * @param parameters 对应 SQL 语句的参数，数据类型为 Object 不定长参数
     * @return SQL 语句执行对应数据表的影响行数。
     */
    public int update(String sql, Object... parameters) {
        // 1. 准备必要的变量
        int affectedRows = 0;
        Connection connection = null;
        PreparedStatement statement = null;

        // 2. 利用 util.JdbcUtils 工具类获取数据库连接对象
        connection = JdbcUtils.getConnection();

        try {
            statement = handlePreparedStatement(connection, sql, parameters);
            // 5. 执行 SQL 语句，得到受影响的行数
            affectedRows = statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JdbcUtils.close(connection, statement);
        }

        return affectedRows;
    }

    /**
     * 查询单行数据，目标执行 SQL 语句转换为指定 JavaBean 规范对象。BeanUtils
     *
     * @param sql        目标执行 SQL 语句
     * @param cls        Class 对象，用于约束当前泛型对应的具体数据类型，同时提供 SQL 转换 JavaBean 规范的目标类型反射操作条件
     * @param parameters SQL 语句对应的参数列表
     * @param <T>        自定义泛型占位符，通过该 Class 类型实际参数约束对应具体数据类型
     * @return 符合 JavaBean 规定对象，如果没有查询到数据，返回 null
     * @throws SQLException SQL 异常
     */
    public <T> T queryBean(String sql, Class<T> cls, Object... parameters) throws SQLException {
        return query(sql, new BeanHandler<>(cls), parameters);
    }

    /**
     * 查询多行数据，每一行数据内容转换为方法约束要求的 JavaBean 对象，多行数据存储到一个 List 集合中
     *
     * @param sql        目标执行 SQL 语句
     * @param cls        Class 对象，用于约束当前泛型对应的具体数据类型，同时提供 SQL 转换 JavaBean 规范的目标类型反射操作条件
     * @param parameters SQL 语句对应的参数列表
     * @param <T>        自定义泛型占位符，通过该 Class 类型实际参数约束对应具体数据类型
     * @return List 集合中包含方法参数约束的 JavaBean 规范对象，如果没有查询到数据，返回 null
     * @throws SQLException SQL 异常
     */
    public <T> List<T> queryBeanList(String sql, Class<T> cls, Object... parameters) throws SQLException {
        return query(sql, new BeanListHandler<>(cls), parameters);
    }

    /**
     * 查询单行数据，数据字段名称对应 Map 的 Key，字段数据对应 Value 因为字段数据类型多样性，Map 中的 Value 选择 Object 类型
     *
     * @param sql        目标执行 select SQL 语句
     * @param parameters SQL 语句对应的参数
     * @return Map 双边队列，对应单行数据内容，如果没有查询到数据，返回 null
     * @throws SQLException SQL 异常
     */
    public Map<String, Object> queryMap(String sql, Object... parameters) throws SQLException {
        return query(sql, new MapHandler(), parameters);
    }

    /**
     * 查询多行数据，每一行数据内容字段作为 Map 的 Key，字段数据作为 Map 的 Value, 多行数据存储到 List 集合中
     *
     * @param sql        目标执行 SQL 语句
     * @param parameters SQL 语句对应的参数列表
     * @return List 集合存储 Map 双边队列，每一个 Map 对应一行数据，如果没有查询到数据，返回 null
     * @throws SQLException SQL 异常
     */
    public List<Map<String, Object>> queryMapList(String sql, Object... parameters) throws SQLException {
        return query(sql, new MapListHandler(), parameters);
    }

    /**
     * 查询单行数据，仅按照字段顺序存储对应的数据内容到一个 Object 类型数组中
     *
     * @param sql        目标执行 SQL 语句
     * @param parameters SQL 语句对应的参数列表
     * @return Object 类型数组对应一行数据，如果没有查询到数据，返回 null
     * @throws SQLException SQL 异常
     */
    public Object[] queryArray(String sql, Object... parameters) throws SQLException {
        return query(sql, new ArrayHandler(), parameters);
    }

    /**
     * 查询多行数据，每一行数据仅按照查询结果集字段顺序存储字段对应数据，数据类型多样，选择 Object 类型数组存储
     * 多行数据对应的结果存储到一个 List 集合中
     *
     * @param sql        目标执行 select SQL 语句
     * @param parameters 语句对应的参数
     * @return List 集合中存储 Object 类型数据，对应多行数据，如果没有数据返回 null
     * @throws SQLException SQL 异常
     */
    public List<Object[]> queryArrayList(String sql, Object... parameters) throws SQLException {
        return query(sql, new ArrayListHandler(), parameters);
    }

    private static PreparedStatement handlePreparedStatement(Connection connection, String sql, Object[] parameters) throws SQLException {
        // 3. 利用数据库连接对象预处理 SQL 语句，得到 PreparedStatement 对象
        PreparedStatement statement = connection.prepareStatement(sql);

        // 4. 【核心模块】 针对于 SQL 语句参数赋值操作
            /*
            什么情况下需要对 SQL 进行参数赋值操作
                1. SQL 有参数
                2. parameters Object 类型不定长参数有数据内容，数据个数和 SQL 参数个数一致
             */
        // 4.1 获取 SQL 语句参数个数
        int parameterCount = statement.getParameterMetaData().getParameterCount();

        // 4.2 条件约束，判断是否需要进行参数赋值操作
        if (parameterCount != 0 && parameters != null && parameters.length == parameterCount) {
            for (int i = 0; i < parameterCount; i++) {
                statement.setObject(i + 1, parameters[i]);
            }
        }

        return statement;
    }

    /**
     * 通用 query 方法，方法所需参数是执行目标 SQL 语句，对应的结果集处理器接口实现类对象，和
     * SQL 语句对应参数
     *
     * @param sql        执行目标 SQL 语句
     * @param rsh        util.ResultSetHandler 结果集处理器接口实现类对象
     * @param parameters SQL 语句对应参数
     * @param <T>        自定义声明泛型
     * @return util.ResultSetHandler 接口实现类约束的返回值类型结果
     * @throws SQLException SQL 异常
     */
    public <T> T query(String sql, ResultSetHandler<T> rsh, Object... parameters) throws SQLException {
        // 1. 必要变量
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        // 2. 获取数据库连接对象
        connection = JdbcUtils.getConnection();

        // 3. 泛型变量，具体数据类型由 util.ResultSetHandler 接口实现类决定
        T t = null;

        try {
            // 4. 处理得到 PreparedStatement ，完成 SQL 语句预处理和参数赋值操作
            statement = handlePreparedStatement(connection, sql, parameters);

            // 5. 执行 SQL 语句，得到 结果集对象
            resultSet = statement.executeQuery();

            // 6. 【核心】利用 util.ResultSetHandler 接口的实现类对象处理 ResultSet 返回目标结果
            t = rsh.handle(resultSet);
        } catch (SQLException e) {
            throw e;
        } finally {
            JdbcUtils.close(connection, statement, resultSet);
        }

        return t;
    }
}
