package com.example.aelp_overall_dashboard.DAOHelper;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.aelp_overall_dashboard.Control.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginDAOHelper {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public boolean isLoggedIn(){
        return (mAuth.getCurrentUser() != null);
    }

    public void login(Context context, String email, String password, OnCompleteListener<AuthResult> handler) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, handler);
    }

    public void register(Context context, final String email, String password, OnCompleteListener<AuthResult> handler){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, handler)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) { }
                        });
                    }
                });
    }
}
