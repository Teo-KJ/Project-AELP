package com.example.aelp_overall_dashboard.Control;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aelp_overall_dashboard.DAOHelper.LoginDAOHelper;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText username = findViewById(R.id.usernameTextBox), password = findViewById(R.id.passwordTextBox);
        Button login = findViewById(R.id.enterButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAccount(username.getText().toString().trim(), password.getText().toString().trim());
            }
        });

    }

    public void loginAccount(String email, String password){
        new LoginDAOHelper().login(Login.this, email, password, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Authentication successful",
                            Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this, MainActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(Login.this, "Authentication failed, please retry",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
