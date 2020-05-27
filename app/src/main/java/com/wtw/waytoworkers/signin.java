package com.wtw.waytoworkers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signin extends AppCompatActivity {
    EditText inputEmail,inputPassword;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private static final String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        TextView signupbtn=(TextView)findViewById(R.id.sign_in_button);
        final TextView forgetpassbtn=findViewById(R.id.forgotPassword);
        inputEmail = (EditText) findViewById(R.id.signinemail);
        inputPassword = (EditText) findViewById(R.id.signinpassword);
        progressBar=(ProgressBar)findViewById(R.id.loginprogressBar);
        Button loginbtn = (Button) findViewById(R.id.ah_login);

        mAuth = FirebaseAuth.getInstance();
        // Checking the email id and password is Empty
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    inputEmail.setError("Please enter email id");
                    inputEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    inputPassword.setError("Enter Password");
                    inputPassword.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                //authenticate user
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(signin.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "signInWithEmail:success");
                                    Intent intent = new Intent(signin.this,homepage.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(signin.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signin.this, chooseor.class));
                finish();
            }
        });
        forgetpassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signin.this,forgetPassword.class));
            }
        });
    }
}
