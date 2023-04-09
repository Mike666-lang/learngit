package util.impl;

import util.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anonymous 2023/3/15 15:16
 */
public class ArrayListHandler implements ResultSetHandler<List<Object[]>> {
    @Override
    public List<Object[]> handle(ResultSet res) throws SQLException {
        // 1. 准备 List 集合
        List<Object[]> list = new ArrayList<>();

        // 2. 结果集获取字段个数
        int columnCount = res.getMetaData().getColumnCount();

        // 3. 多行数据，使用 while 循环
        while (res.next()) {
            // 4. 每一次循环对应一行数据，数组的容量为字段个数
            Object[] arr = new Object[columnCount];

            for (int i = 0; i < arr.length; i++) {
                arr[i] = res.getObject(i + 1);
            }

            list.add(arr);
        }

        return list.isEmpty() ? null : list;
    }
}
