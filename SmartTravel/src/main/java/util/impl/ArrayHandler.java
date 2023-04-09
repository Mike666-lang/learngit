package util.impl;


import util.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Anonymous 2023/3/15 16:21
 */
public class ArrayHandler implements ResultSetHandler<Object[]> {
    @Override
    public Object[] handle(ResultSet res) throws SQLException {
        List<Object[]> handle = new ArrayListHandler().handle(res);

        return handle != null ? handle.get(0) : null;
    }
}
