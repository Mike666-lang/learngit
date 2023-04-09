package util;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetHandler<T> {
    /**
     * 结果集处理规范方法，用于处理 java.sql.ResultSet 查询结果集
     *
     * @param res java.sql.ResultSet 查询结果集对象
     * @return 泛型约束，由实现类决定最终的返回数据
     * @throws SQLException SQL 异常
     */
    T handle(ResultSet res) throws SQLException;
}