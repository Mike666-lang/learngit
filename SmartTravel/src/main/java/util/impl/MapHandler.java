package util.impl;

import util.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 结果集处理结果为单行数据对应的 Map 双边队列
 *
 * @author Anonymous 2023/3/15 14:48
 */
public class MapHandler implements ResultSetHandler<Map<String, Object>> {
    @Override
    public Map<String, Object> handle(ResultSet res) throws SQLException {

        List<Map<String, Object>> mapList = new MapListHandler().handle(res);
        return mapList != null ? mapList.get(0) : null;
    }
}
