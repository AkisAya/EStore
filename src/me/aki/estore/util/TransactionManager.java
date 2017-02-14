package me.aki.estore.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.DbUtils;


import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Aki on 2017/2/13.
 */
public class TransactionManager {

    // 唯一的数据源
    private static DataSource dataSource = new ComboPooledDataSource();

    private static ThreadLocal<Connection> localProxyConn = new ThreadLocal<>();
    private static ThreadLocal<Connection> localRealConn = new ThreadLocal<>();
    private static ThreadLocal<Boolean> hasOpenedTransaction = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    private TransactionManager() {}


    public static void startTransaction() throws SQLException {
        hasOpenedTransaction.set(true);
        final Connection connection = dataSource.getConnection();
        localRealConn.set(connection);
        connection.setAutoCommit(false);


        // QueryRunner的方法执行完毕后会默认关闭连接，导致后续的dao方法在执行事务的时候用的同一个连接是关闭的导致出错
        // 所以这里给连接做个代理使关闭无效，最后释放的时候再关闭连接
        Connection proxyConn = (Connection) Proxy.newProxyInstance(connection.getClass().getClassLoader(), connection.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if ("close".equals(method.getName())) {
                            return null;
                        } else {
                            return method.invoke(connection, args);
                        }
                    }
                });

        localProxyConn.set(proxyConn);
    }

    /**
     * 对没开启事务的方法返回普通的数据源
     * 开启了事务的方法，代理其getConnection方法，返回一个开启了事务的connection
     * @return
     */
    public static DataSource getDataSource() {
        if (hasOpenedTransaction.get()) { // 如果开启了事务
            return (DataSource) Proxy.newProxyInstance(dataSource.getClass().getClassLoader(), new Class[]{DataSource.class},
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            if ("getConnection".equals(method.getName())) {
                                return localProxyConn.get();
                            } else {
                                return method.invoke(dataSource, args);
                            }
                        }
                    });
        } else { // 没有开启过事务返回普通的数据源
            return dataSource;
        }
    }

    public static void commit() {
        DbUtils.commitAndCloseQuietly(localProxyConn.get());
    }

    public static void rollback() {
        DbUtils.rollbackAndCloseQuietly(localProxyConn.get());
    }

    public static void release() {
        DbUtils.closeQuietly(localRealConn.get());
        localRealConn.remove();
        localProxyConn.remove();
        hasOpenedTransaction.remove();
    }
}
