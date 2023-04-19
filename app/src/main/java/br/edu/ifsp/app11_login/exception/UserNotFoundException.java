package br.edu.ifsp.app11_login.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String username) {
        super("User not found" + username);
    }
}
