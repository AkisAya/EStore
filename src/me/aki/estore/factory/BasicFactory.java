package me.aki.estore.factory;

import me.aki.estore.dao.UserDao;

import java.io.IOException;
import java.io.InputStream;
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

    public  <T> T getInstance(Class<T> clazz) {
        String interfaceName = clazz.getSimpleName();
        String implName = properties.getProperty(interfaceName);
        try {
            return (T) Class.forName(implName).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("生成实例失败", e);
        }
    }


    public static void main(String[] args) {
//        getInstance(UserDao.class);
    }
}
