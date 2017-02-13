package me.aki.estore.factory;

import me.aki.estore.annotation.OpenTransaction;
import me.aki.estore.dao.Dao;
import me.aki.estore.service.Service;
import me.aki.estore.util.TransactionManager;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Properties;

/**
 * Created by Aki on 2017/2/10.
 */
public class BasicFactory {
    private static BasicFactory factory = new BasicFactory();
    private static Properties properties = null;
    private BasicFactory(){}

    static {
        try {
            InputStream inputStream = BasicFactory.class.getClassLoader().getResourceAsStream("config.properties");
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("读取配置文件失败", e);
        }

    }



    public static BasicFactory getFactory() {
        return factory;
    }

    public  <T extends Dao> T getDao(Class<T> clazz) {
        String interfaceName = clazz.getSimpleName();
        String implName = properties.getProperty(interfaceName);
        try {
            return (T) Class.forName(implName).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("生成实例失败", e);
        }
    }


    public  <T extends Service> T getService(Class<T> clazz) {

        try{
            //--根据配置文件创建具体的Service
            String interfaceName = clazz.getSimpleName();
            String implName = properties.getProperty(interfaceName);
            final T service = (T) Class.forName(implName).newInstance();

            // 利用代理实现AOP
            @SuppressWarnings("unchecked")
            T proxyService = (T) Proxy.newProxyInstance(service.getClass().getClassLoader(),
                    service.getClass().getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            if (method.isAnnotationPresent(OpenTransaction.class)) {
                                try {
                                    TransactionManager.startTransaction();
                                    // 执行service的方法
                                    Object obj = method.invoke(service, args);
                                    TransactionManager.commit();
                                    return obj;
                                } catch (InvocationTargetException e) {
                                    TransactionManager.rollback();//--回滚事务
                                    throw new RuntimeException(e.getTargetException());
                                } catch (Exception e) {
                                    TransactionManager.rollback();
                                    throw new RuntimeException(e);
                                } finally {
                                    TransactionManager.release();
                                }
                            } else {
                                return method.invoke(service, args);
                            }
                        }
                    });

            return proxyService;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("生成实例失败", e);
        }

    }
}
