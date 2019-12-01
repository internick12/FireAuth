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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private EditText input_email;
    private Button btnResetPass;
    private TextView btbBack;
    private RelativeLayout activity_forgot;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        //View
        input_email = findViewById(R.id.forgot_email);
        btnResetPass = findViewById(R.id.forgot_btn_reset);
        btbBack = findViewById(R.id.forgot_btn_back);
        activity_forgot = findViewById(R.id.activity_forgot_password);

        btnResetPass.setOnClickListener(this);
        btbBack.setOnClickListener(this);

        //init firebase
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.forgot_btn_back){
            startActivity(new Intent(ForgotPassword.this, MainActivity.class));
            finish();
        } else  if(v.getId() == R.id.forgot_btn_reset) {
            resetPassword(input_email.getText().toString());
        }
    }

    private void resetPassword(final String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Snackbar snackbar = Snackbar.make(activity_forgot, "We send password to email:" + email, Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        } else {
                            Snackbar snackbar = Snackbar.make(activity_forgot, "failed to send pasword", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                    }
                });
    }
}
