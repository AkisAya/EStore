package me.aki.estore.service;

import me.aki.estore.dao.UserDao;
import me.aki.estore.domain.User;
import me.aki.estore.exception.UserException;
import me.aki.estore.factory.BasicFactory;
import me.aki.estore.util.SendJMail;

import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by Aki on 2017/2/10.
 */
public class UserServiceImpl implements UserService {
    UserDao userDao = BasicFactory.getFactory().getDao(UserDao.class);

    @Override
    public void register(User user) throws UserException {
        try {
            // 校验用户名是否存在
            if (userDao.findUserByName(user) != null) {
                throw new UserException("用户名已存在");
            }

            // 添加用户
            user.setActivecode(UUID.randomUUID().toString());
            user.setRole("user");   // 普通用户
            user.setState(0);       // 处于未激活状态
            userDao.addUser(user);
            // 发送激活邮件
            String emailMsg = "注册成功，请在24小时之内<a href='http://localhost:8080/estore/servlet/ActiveUserServlet?activecode=" +
                    user.getActivecode() + "'>激活</a>。如果点击链接不能跳转，请复制以下链接到浏览器地址<br>" +
                    "http://localhost:8080/estore/servlet/ActiveUserServlet?activecode=" + user.getActivecode();
            new Thread(new SendJMail(user.getEmail(), emailMsg)).start();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("查询用户或添加用户出错");
        }
    }

    @Override
    public User activeUser(String activeCode) throws UserException {

        User user = null;
        try {
            user = userDao.findUserByCode(activeCode);

            if (user == null) {
                throw new UserException("激活码错误");
            }

            if (user.getState() == 1) {
                throw new UserException("用户已经激活，请不要重复激活");
            }

            // 如果查询到了用户，判断激活码有没有过期
            long duration = System.currentTimeMillis() - user.getUpdatetime().getTime();
            if (duration > 24*3600*1000) {
                // 激活码过期
                userDao.deleteUserById(user.getId());
                throw new UserException("激活码过期，请重新注册并在24小时内激活");
            } else {
                // 更新用户激活状态
                userDao.updateStateById(user.getId());
                // 避免重新查询，直接更改State后返回
                user.setState(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User login(String username, String password) throws UserException {
        try {
            // 先查找用户
            User user = userDao.findUserByNameAndPwd(username, password);
            if (user == null) {
                throw new UserException("用户名或密码错误");
            } else if (user.getState() == 0) {
                throw new UserException("用户账户为激活，请查看注册邮箱激活");
            } else {
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
//            throw new UserException("查询出错,服务器正在开小差");
        }
        return null;
    }

    @Override
    public User findUserByNameAndPwd(String username, String password) throws UserException {
        try {
            return userDao.findUserByNameAndPwd(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
//            throw new UserException("查询出错,服务器正在开小差");
        }
        return null;
    }
}
