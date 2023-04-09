package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;

/**
 * JDBC 资源管理工具类
 * 1. 资源自动加载
 * 2. 数据库连接对象获取操作，并且简化操作流程
 * 3. 数据库相关操作资源(Connection Statement ResultSet)关闭方法
 *
 * @author Anonymous 2023/3/14 16:06
 */
public class JdbcUtils {
    /**
     * 用于存储 JDBC 协议的 URL 连接
     */
    private static String jdbcUrl;

    /**
     * 当前数据库连接操作对应的用户名
     */
    private static String username;

    /**
     * 当前数据库连接操作对应的密码
     */
    private static String password;

    /*
    利用静态代码块在类文件加载阶段的必须执行的特征，完成自动化配置加载，驱动加载操作
     */
    static {
        // 1. 找到 src 目录下 或者后期 Maven 项目 resource 资源目录下的配置文件 jdbc.properties
        InputStream input = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");

        // 2. 实例化 Properties 对象，底层数据存储结构为 Map 键值对结构
        Properties properties = new Properties();

        try {
            // 3. Properties 对象加载对应配置文件字节输入流对象，完成配置文件自动解析
            properties.load(input);

            // 4. 数据库连接必要的参数提供
            jdbcUrl = properties.getProperty("jdbcUrl");
            username = properties.getProperty("username");
            password = properties.getProperty("password");

            // 5. 加载驱动
            Class.forName(properties.getProperty("driverClass"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // 6. 关闭文件资源
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取数据库连接对象方法
     *
     * @return java.sql.Connection 数据库连接对象
     */
    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * 关闭资源方法
     *
     * @param connection java.sql.Connection 数据库连接对象资源
     * @param statement  java.sql.Statement 数据库 SQL 语句搬运工资源
     */
    public static void close(Connection connection, Statement statement) {
        close(statement, connection);
    }

    /**
     * 关闭资源方法
     *
     * @param connection java.sql.Connection 数据库连接对象资源
     * @param statement  java.sql.Statement 数据库 SQL 语句搬运工资源
     * @param resultSet  java.sql.ResultSet 数据库查询结果集对象
     */
    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        close(resultSet, statement, connection);
    }

    /**
     * 内部私有化方法，统一处理数据库相关资源
     *
     * @param resources AutoCloseable 不定长参数，统一关闭对应资源
     */
    private static void close(AutoCloseable... resources) {
        if (resources != null && resources.length > 0) {
            Arrays.stream(resources).forEach(source -> {
                try {
                    if (source != null) {
                        source.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}