package com.juborajsarker.helpmate.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.juborajsarker.helpmate.R;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private SharedPreferences sharedPreferences;

    String uid = "";

    EditText emailET, passwordET;
    TextView forgotPasswordTV, notRegisteredTV;
    Button loginBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("loginStatus", MODE_PRIVATE);
        Boolean isLogedIn = sharedPreferences.getBoolean("isLogedIn", false);

        if (isLogedIn){

            startActivity(new Intent(LoginActivity.this, BottomNavigationActivity.class));
            finish();
        }

        firebaseAuth = FirebaseAuth.getInstance();

        emailET = (EditText) findViewById(R.id.email_ET);
        passwordET = (EditText) findViewById(R.id.password_ET);

        forgotPasswordTV = (TextView) findViewById(R.id.forgot_password_TV);
        notRegisteredTV = (TextView) findViewById(R.id.not_registered_TV);

        loginBTN = (Button) findViewById(R.id.login_BTN);

        forgotPasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
            }
        });


        notRegisteredTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });


        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emptyValidation();

                if (emptyValidation()){

                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("Checking information\nPlease wait.....");
                    progressDialog.show();

                    final String email = emailET.getText().toString();
                    String password = passwordET.getText().toString();

                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){

                                Toast.makeText(LoginActivity.this, "Login Success!!!", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                if (user != null){

                                    uid = user.getUid();
                                }

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("isLogedIn", true);
                                editor.commit();

                                progressDialog.dismiss();
                                Intent intent = new Intent(LoginActivity.this, BottomNavigationActivity.class);
                                intent.putExtra("uid", uid);
                                intent.putExtra("email", email);
                                startActivity(intent);
                                finish();

                            } else {

                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });


                }else {

                    if (emailET.getText().toString().equals("")){}

                    emailET.setError("This field is required !!!");
                }

                if (passwordET.getText().toString().equals("")){


                    passwordET.setError("This field is required !!!");
                }

            }
        });

    }

    private boolean emptyValidation() {

        if (emailET.getText().toString().equals("") || passwordET.getText().toString().equals("")){

           return false;

        }else {

            return true;
        }
    }
}
