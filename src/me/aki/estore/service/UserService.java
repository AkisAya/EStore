package me.aki.estore.service;

import me.aki.estore.annotation.OpenTransaction;
import me.aki.estore.domain.User;
import me.aki.estore.exception.UserException;

/**
 * Created by Aki on 2017/2/10.
 */
public interface UserService extends Service {
    @OpenTransaction
    void register(User user) throws UserException;

    User activeUser(String activeCode) throws UserException;

    User login(String username, String password) throws UserException;

    User findUserByNameAndPwd(String username, String password) throws UserException;
}
