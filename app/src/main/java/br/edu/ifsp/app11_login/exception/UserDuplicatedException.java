package br.edu.ifsp.app11_login.exception;

public class UserDuplicatedException extends Exception{
    public UserDuplicatedException(String username){
        super("Duplicate user on database" + username);
    }
}
