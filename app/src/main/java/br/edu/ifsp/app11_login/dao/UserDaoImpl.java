package br.edu.ifsp.app11_login.dao;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.app11_login.exception.UserDuplicatedException;
import br.edu.ifsp.app11_login.model.User;

public class UserDaoImpl implements IUserDao{
    private static UserDaoImpl instance = null;
    private List<User> dataset;
    private UserDaoImpl(){
        dataset = new ArrayList<>();
    }

    public static UserDaoImpl getInstance(){
        if(instance == null){
            instance = new UserDaoImpl();
        }
        return instance;
    }
    @Override
    public boolean create(User user) throws UserDuplicatedException {
        if(user != null){
            User inDataset = dataset.stream()
                    .filter(user1 -> user1.getUsername().equals(user.getUsername()))
                    .findAny()
                    .orElse(null);
            if(inDataset != null){
                throw new UserDuplicatedException(user.getUsername());
            }
            dataset.add(user);
            return true;
        }
        return false;
    }

    @Override
    public User findByUsername(String username){
        return dataset.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> findAll() {
        return dataset;
    }
}
