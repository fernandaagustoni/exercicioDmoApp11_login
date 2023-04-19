package br.edu.ifsp.app11_login.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifsp.app11_login.R;
import br.edu.ifsp.app11_login.controller.UserAddController;
import br.edu.ifsp.app11_login.exception.IllegalInputException;
import br.edu.ifsp.app11_login.exception.UserDuplicatedException;

public class UserAddActivity extends AppCompatActivity {
    private UserAddController controller;
    private EditText username;
    private EditText password;
    private EditText passwordConfirmation;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);

        controller = new UserAddController(this);
        findById();
        setListener();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void findById(){
        username = findViewById(R.id.edittext_new_username);
        password = findViewById(R.id.edittext_new_password);
        passwordConfirmation = findViewById(R.id.edittext_new_confirm_password);
        mButton = findViewById(R.id.button_save_user);
    }

    private void saveUser(){
        boolean result = false;
        String user, pass, confirm;
        user = username.getText().toString();
        pass = password.getText().toString();
        confirm = passwordConfirmation.getText().toString();

        try{
            result = controller.insertUser(user, pass, confirm);
        } catch (UserDuplicatedException e) {
            Toast.makeText(this, getString(R.string.error_duplicated_user), Toast.LENGTH_SHORT).show();
        } catch (IllegalInputException e) {
            Toast.makeText(this, getString(R.string.error_invalid_input), Toast.LENGTH_SHORT).show();
        }

        if(result){
            Toast.makeText(this, getString(R.string.insert_sucess), Toast.LENGTH_SHORT).show();
            finish();
        }else{
            password.setText("");
            passwordConfirmation.setText("");
            username.setText("");
        }
    }

    private void setListener(){
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });
    }
}
