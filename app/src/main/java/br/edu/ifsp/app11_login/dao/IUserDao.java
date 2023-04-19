package br.edu.ifsp.app11_login.dao;

import java.util.List;

import br.edu.ifsp.app11_login.exception.UserDuplicatedException;
import br.edu.ifsp.app11_login.model.User;

public interface IUserDao {
    boolean create(User user) throws UserDuplicatedException;
    User findByUsername(String username);
    List<User> findAll();
}
