package br.edu.ifsp.app11_login.model;

import android.util.Log;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User implements Serializable {
    private static final String TAG = "APP 11: User";
    private String username;
    private String password;

    public User(){

    }
    public User(String username, String password) {
        this.username = username;
        generatePassword(password);
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    private void generatePassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte bytes[] = messageDigest.digest(password.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();
            for(byte b : bytes){
                sb.append(String.format("%02X", 0xFF & b));
            }
            this.password = sb.toString();
        }catch (NoSuchAlgorithmException noSuchAlgorithmException){
            Log.e(TAG, "Error algorithm SHA-256.");
            this.password = password;
        }catch (UnsupportedEncodingException unsupportedEncodingException){
            Log.e(TAG, "Error encoding process.");
            this.password = password;
        }
    }

    public static boolean autenticate(User userSystem, User userLogin){
        return userSystem.equals(userLogin);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        User other = (User) obj;
        boolean equals = false;
        if(other.getUsername().equals(this.username)){
            if(other.getPassword().equals(this.password)){
                equals = true;
            }
        }
        return equals;
    }
}
