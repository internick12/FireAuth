package com.galosoft.fireauth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin;
    EditText input_email, input_pasword;
    TextView btnSignUp, btnForgotPassword;
    RelativeLayout activity_main;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //View
        btnLogin = findViewById(R.id.login_btn_login);
        input_email = findViewById(R.id.login_email);
        input_pasword = findViewById(R.id.login_password);
        btnSignUp = findViewById(R.id.login_btn_signup);
        btnForgotPassword = findViewById(R.id.login_btn_forgot_password);
        activity_main = findViewById(R.id.activity_main);

        btnSignUp.setOnClickListener(this);
        btnForgotPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        //Init firebase auth
        auth = FirebaseAuth.getInstance();
        //Session active
        //if(auth.getCurrentUser() != null)
         //   startActivity(new Intent(MainActivity.this, DashBoard.class));
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login_btn_forgot_password) {
            startActivity(new Intent(MainActivity.this, ForgotPassword.class));
            finish();
        } else if(v.getId() == R.id.login_btn_signup) {
            startActivity(new Intent(MainActivity.this, SignUp.class));
            finish();
        } else if(v.getId() == R.id.login_btn_login) {
            loginUser(input_email.getText().toString(), input_pasword.getText().toString());    
        }
    }

    private void loginUser(String email, final String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            if(password.length() <6) {
                                Snackbar snackbar = Snackbar.make(activity_main, "Password length must be over 6", Snackbar.LENGTH_SHORT);
                                snackbar.show();
                            }
                        } else {
                            startActivity(new Intent(MainActivity.this, DashBoard.class));
                        }
                    }
                });
    }
}
