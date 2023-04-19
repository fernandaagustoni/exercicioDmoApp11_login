package br.edu.ifsp.app11_login.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.edu.ifsp.app11_login.R;
import br.edu.ifsp.app11_login.controller.MainController;
import br.edu.ifsp.app11_login.exception.IllegalInputException;
import br.edu.ifsp.app11_login.exception.UserNotFoundException;

public class MainActivity extends AppCompatActivity {

    private MainController controller;
    private ConstraintLayout mLayout;
    private Button mButton;
    private CheckBox mCheckBox;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private FloatingActionButton mFloatingActionButton;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findById();
        setListener();
        getSupportActionBar().hide();

    }

    @Override
    protected void onResume() {
        controller = new MainController(this);
        controller.checkPreferences(mUsernameEditText, mPasswordEditText, mCheckBox, mLayout);
        super.onResume();
    }

    @Override
    protected void onStop() {
        mUsernameEditText.setText("");
        mPasswordEditText.setText("");
        super.onStop();
    }

    private void addUser(){
        Intent intent = new Intent(this, UserAddActivity.class);
        startActivity(intent);
    }

    private void changeColor(){
        controller.chanceColor(mLayout);
    }

    private void findById(){
        mButton = findViewById(R.id.button_login);
        mCheckBox = findViewById(R.id.check_remember_login);
        mUsernameEditText = findViewById(R.id.edittext_username);
        mPasswordEditText = findViewById(R.id.edittext_password);
        mFloatingActionButton = findViewById(R.id.btn_add_user);
        mTextView = findViewById(R.id.text_change_color);
        mLayout = findViewById(R.id.main_layout);
    }

    private void login(){
        String username = mUsernameEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        try{
            controller.login(username, password, mCheckBox.isChecked());
        } catch (UserNotFoundException e) {
            Toast.makeText(this, getString(R.string.error_user_not_found), Toast.LENGTH_SHORT).show();
        } catch (IllegalInputException e) {
            Toast.makeText(this, getString(R.string.error_invalid_input), Toast.LENGTH_SHORT).show();
        }
    }

    private void setListener(){
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
            }
        });
    }
}
