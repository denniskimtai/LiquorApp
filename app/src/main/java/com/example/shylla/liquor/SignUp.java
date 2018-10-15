package com.example.shylla.liquor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private EditText signupEmail, signupPassword;
    private TextView txtViewSignup, txtViewLogin;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Get firebase authentication instance
        auth = FirebaseAuth.getInstance();

        signupEmail = findViewById(R.id.signupEmail);
        signupPassword = findViewById(R.id.signupPassword);
        txtViewSignup = findViewById(R.id.txtViewSignup);
        txtViewLogin = findViewById(R.id.txtViewLogin);
        progressBar = findViewById(R.id.progressBar);

        //On click listeners
        txtViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
            }
        });

        txtViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signupEmail.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();

                //Check if email field is empty
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(SignUp.this, "Please enter your Email Address", Toast.LENGTH_SHORT).show();
                    signupEmail.setError("Please enter your password");
                    return;
                }

                //Check if password field is empty
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(SignUp.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    signupPassword.setError("Please enter your password");
                    return;
                }

                //Check passowrd length should be above 6 characters
                if (password.length() < 6){
                    Toast.makeText(SignUp.this, "Password too short! Please enter more than 6 characters", Toast.LENGTH_SHORT).show();
                    signupPassword.setError("Password too short!");
                }

                //Progress bar visibility set to visible
                progressBar.setVisibility(View.VISIBLE);

                //Create a new user with email and password
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Set visibility of progress bar to gone
                                progressBar.setVisibility(View.GONE);

                                //Check if sign up is successfull
                                //Intent to homepage if successfull
                                //Notify user if not successfull

                                if (!task.isSuccessful()){
                                    Toast.makeText(SignUp.this, "Sign up failed! Please try again.", Toast.LENGTH_SHORT).show();
                                }else {
                                    //Intent to homepage
                                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
