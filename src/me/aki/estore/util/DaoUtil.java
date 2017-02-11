package me.aki.estore.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Aki on 2017/2/10.
 */
public class DaoUtil {

    private static DataSource source = new ComboPooledDataSource();
    // 使用C3P0数据源

    private DaoUtil() {
    }

    public static DataSource getSource(){
        return source;
    }

    public static Connection getConn(){
        try {
            return source.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
