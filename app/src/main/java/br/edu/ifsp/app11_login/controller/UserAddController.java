package br.edu.ifsp.app11_login.controller;

import android.content.Context;

import br.edu.ifsp.app11_login.dao.IUserDao;
import br.edu.ifsp.app11_login.dao.UserDaoImpl;
import br.edu.ifsp.app11_login.dao.UserDaoJson;
import br.edu.ifsp.app11_login.exception.IllegalInputException;
import br.edu.ifsp.app11_login.exception.UserDuplicatedException;
import br.edu.ifsp.app11_login.model.User;

public class UserAddController {
    private Context context;
    private IUserDao data;

    public UserAddController(Context context) {
        this.context = context;
        //data = UserDaoImpl.getInstance();
        data = new UserDaoJson(context);
    }

    public boolean insertUser(String username, String password, String confirmation) throws UserDuplicatedException, IllegalInputException {
        if(username.isEmpty() || password.isEmpty() || confirmation.isEmpty()){
            throw new IllegalInputException("Existem dados não preenchidos.");
        }

        if(!password.equals(confirmation)){
            throw new IllegalInputException("Senhas não conferem.");
        }

        User user = new User(username, password);
        try {
            return data.create(user);
        } catch (UserDuplicatedException e) {
            throw e;
        }
    }
}

