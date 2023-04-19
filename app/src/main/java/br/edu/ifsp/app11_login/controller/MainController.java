package br.edu.ifsp.app11_login.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.constraintlayout.widget.ConstraintLayout;

import br.edu.ifsp.app11_login.R;
import br.edu.ifsp.app11_login.constant.Constants;
import br.edu.ifsp.app11_login.dao.IUserDao;
import br.edu.ifsp.app11_login.dao.UserDaoImpl;
import br.edu.ifsp.app11_login.dao.UserDaoJson;
import br.edu.ifsp.app11_login.exception.IllegalInputException;
import br.edu.ifsp.app11_login.exception.UserNotFoundException;
import br.edu.ifsp.app11_login.model.User;
import br.edu.ifsp.app11_login.view.WorkingActivity;

public class MainController {

    private Context context;
    private IUserDao data;

    public MainController(Context context) {
        this.context = context;
        //data = UserDaoImpl.getInstance();
        data = new UserDaoJson(context);
    }

    public void chanceColor(ConstraintLayout layout){
        boolean defaultBack;
        SharedPreferences preferences = context.getSharedPreferences(Constants.BACKGROUND_PREFERENCES, Context.MODE_PRIVATE);
        defaultBack = preferences.getBoolean(Constants.KEY_BACK_DEFAULT, false);
        defaultBack = !defaultBack;

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constants.KEY_BACK_DEFAULT, defaultBack);
        editor.commit();

        if(defaultBack){
            layout.setBackgroundColor(context.getColor(R.color.app_background));
        }else{
            layout.setBackgroundColor(context.getColor(R.color.app_background2));
        }
    }

    public void checkPreferences(EditText user, EditText pass, CheckBox remember, ConstraintLayout layout){
        String username, password;
        boolean savePrefs;
        SharedPreferences preferences = context.getSharedPreferences(Constants.LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        savePrefs = preferences.getBoolean(Constants.KEY_PREFS, false);
        if(savePrefs){
            username = preferences.getString(Constants.KEY_USER, "");
            password = preferences.getString(Constants.KEY_PASS, "");
            user.setText(username);
            pass.setText(password);
        }
        remember.setChecked(savePrefs);

        boolean defaultBack;
        preferences = context.getSharedPreferences(Constants.BACKGROUND_PREFERENCES, Context.MODE_PRIVATE);
        defaultBack = preferences.getBoolean(Constants.KEY_BACK_DEFAULT, false);
        if(defaultBack){
            layout.setBackgroundColor(context.getColor(R.color.app_background));
        }else{
            layout.setBackgroundColor(context.getColor(R.color.app_background2));
        }

    }

    public void login(String username, String password, boolean savePreference) throws UserNotFoundException, IllegalInputException {
        if(username.isEmpty() || password.isEmpty()){
            throw new IllegalInputException("Illegal data input to login.");
        }

        User loginUser = new User(username, password);
        User search = data.findByUsername(username);
        if(search == null) {
            throw new UserNotFoundException(username);
        }
        if(User.autenticate(search, loginUser)){
            remember(username, password, savePreference);
            openWorkingActivity(search);
        }else{
            throw new IllegalInputException("Illegal data input to login.");
        }
    }

    private void openWorkingActivity(User u){
        Intent intent = new Intent(context, WorkingActivity.class);
        intent.putExtra(Constants.KEY_USER, u.getUsername());
        context.startActivity(intent);
    }

    private void remember(String username, String password, boolean savePreference){
        SharedPreferences preferences = context.getSharedPreferences(Constants.LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if(savePreference){
            editor.putString(Constants.KEY_USER, username);
            editor.putString(Constants.KEY_PASS, password);
        }else{
            editor.putString(Constants.KEY_USER, "");
            editor.putString(Constants.KEY_PASS, "");
        }
        editor.putBoolean(Constants.KEY_PREFS, savePreference);
        editor.commit();
    }
}

