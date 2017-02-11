package me.aki.estore.dao;

import me.aki.estore.domain.User;
import me.aki.estore.util.DaoUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Aki on 2017/2/10.
 */
public class UserDaoImpl implements UserDao {
    @Override
    public User findUserByName(User user, Connection connection) throws SQLException {
        String sql = "select * from users where username = ?";
        QueryRunner queryRunner = new QueryRunner();
        return queryRunner.query(connection, sql, new BeanHandler<User>(User.class), user.getUsername());
    }

    @Override
    public void addUser(User user, Connection connection) throws SQLException {
        String sql = "insert into users(username, password, nickname, email, role, state, activecode) values(?, ?, ?, ?, ?, ?, ?)";
        QueryRunner queryRunner = new QueryRunner();
        queryRunner.update(connection, sql, user.getUsername(), user.getPassword(), user.getNickname(),
                user.getEmail(), user.getRole(), user.getState(), user.getActivecode());
    }

    @Override
    public User findUserByCode(String activeCode) throws SQLException {
        String sql = "select * from users where activecode = ?";
        QueryRunner queryRunner = new QueryRunner(DaoUtil.getSource());
        return queryRunner.query(sql, new BeanHandler<User>(User.class), activeCode);
    }

    @Override
    public void deleteUserById(int id) throws SQLException {
        String sql = "delete from users where id = ?";
        QueryRunner queryRunner = new QueryRunner(DaoUtil.getSource());
        queryRunner.update(sql, id);

    }

    @Override
    public void updateStateById(int id) throws SQLException {
        String sql = "update users set state = 1 where id = ?";
        QueryRunner queryRunner = new QueryRunner(DaoUtil.getSource());
        queryRunner.update(sql, id);
    }

    @Override
    public User findUserByNameAndPwd(String username, String password) throws SQLException {
        String sql = "select * from users where username = ? and password = ?";
        QueryRunner queryRunner = new QueryRunner(DaoUtil.getSource());
        return queryRunner.query(sql, new BeanHandler<User>(User.class), username, password);
    }
}
