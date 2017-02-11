package me.aki.estore.dao;

import me.aki.estore.domain.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Aki on 2017/2/10.
 */
public interface UserDao {
    User findUserByName(User user, Connection connection) throws SQLException;

    void addUser(User user, Connection connection) throws SQLException;

    User findUserByCode(String activeCode) throws SQLException;

    void deleteUserById(int id) throws SQLException;

    void updateStateById(int id) throws SQLException;

    User findUserByNameAndPwd(String username, String password) throws SQLException;
}
