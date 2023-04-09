package util.impl;

import util.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Anonymous 2023/3/15 16:21
 */
public class MapListHandler implements ResultSetHandler<List<Map<String, Object>>> {
    @Override
    public List<Map<String, Object>> handle(ResultSet res) throws SQLException {
        List<Map<String, Object>> mapList = new ArrayList<>();

        // 1. 获取结果集元数据对象
        ResultSetMetaData metaData = res.getMetaData();

        // 2. 获取字段个数
        int columnCount = metaData.getColumnCount();
        while (res.next()) {
            // 3. 实例化 Map 双边队列，键值对个数为 columnCount
            HashMap<String, Object> map = new HashMap<>(columnCount);

            for (int i = 1; i <= columnCount; i++) {
                // 4. 从元数据中获取对应的字段名称，并且从结果集中获取对应的字段数据，存储到 Map 中
                map.put(metaData.getColumnName(i), res.getObject(i));
            }

            mapList.add(map);
        }

        return mapList.isEmpty() ? null : mapList;
    }
}
