package br.edu.ifsp.app11_login.exception;

public class IllegalInputException extends Exception{
    public IllegalInputException(String username) {
        super("Ilegal input" + username);
    }
}
