package br.edu.ifsp.app11_login.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import br.edu.ifsp.app11_login.R;
import br.edu.ifsp.app11_login.constant.Constants;

public class WorkingActivity extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString(Constants.KEY_USER);

        mTextView = findViewById(R.id.text_user_logged);
        mTextView.setText("Olá " + name);

    }
}