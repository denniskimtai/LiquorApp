package com.example.shylla.liquor;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private TextView txtViewLogin, txtViewSignup;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get firebase authentication instance
        //Check if current user is still logged in
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null){
            //Intent to start homepage
            Intent intent = new Intent(MainActivity.this , CategoriesActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.activity_main);

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        txtViewSignup = findViewById(R.id.txtSignup);
        txtViewLogin = findViewById(R.id.txtLogin);
        progressBar = findViewById(R.id.progressBar);

        //Get firebase authentication instance
        auth = FirebaseAuth.getInstance();
        txtViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent to go to homepage
            }
        });

        txtViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        txtViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = loginEmail.getText().toString().trim();
                final String password = loginPassword.getText().toString().trim();

                //progressBar.setVisibility(View.VISIBLE);

                //Authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                               // progressBar.setVisibility(View.GONE);

                                //Check for success of operation
                                //If successful move to homepage else toast a message

                                if (!task.isSuccessful()){
                                    Toast.makeText(MainActivity.this, "Login failed! Please check your internet connection.", Toast.LENGTH_SHORT).show();
                                } else{
                                    //Intent to proceed to homepage
                                    Intent intent = new Intent(MainActivity.this , CategoriesActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

            }
        });

    //Gradient animation
        RelativeLayout relativeLayout = findViewById(R.id.loginScreen);
        AnimationDrawable animationDrawable =  (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();


    }
}
