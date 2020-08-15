package com.example.aelp_overall_dashboard.Control;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aelp_overall_dashboard.DAOHelper.LoginDAOHelper;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText username = findViewById(R.id.usernameTextBox), password = findViewById(R.id.passwordTextBox);
        ImageButton back = findViewById(R.id.backButton);
        final Button register = findViewById(R.id.enterButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerAccount(username.getText().toString().trim(), password.getText().toString().trim());
            }
        });
    }

    public void registerAccount(String email, String password){
        new LoginDAOHelper().register(Register.this, email, password, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(Register.this, "Account registered",
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Register.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
