package com.debug.messagereceiver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class loginactivity extends AppCompatActivity {

    private EditText mEmailField;
    private EditText mPasswordfField;
    private Button mLoginBtn,signupp;

    FirebaseAuth mAuth;
    private  FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        mAuth = FirebaseAuth.getInstance();

        mEmailField = (EditText) findViewById(R.id.EmailField);
        mPasswordfField = (EditText) findViewById(R.id.PasswordField);
        signupp = (Button) findViewById(R.id.signup);
        mLoginBtn = (Button) findViewById(R.id.Loginbutton);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()!= null){

                    //startActivity(new Intent(MainActivity.this,Other.class));
                    Intent activity = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(activity);
                }

            }
        };
        signupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = mEmailField.getText().toString();
                String userPaswd = mPasswordfField.getText().toString();
                if (userEmail.isEmpty()) {
                    mEmailField.setError("Provide your Email first!");
                    mEmailField.requestFocus();
                } else if (userPaswd.isEmpty()) {
                    mPasswordfField.setError("Enter Password!");
                    mPasswordfField.requestFocus();
                } else if (userEmail.isEmpty() && userPaswd.isEmpty()) {
                    Toast.makeText(loginactivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(userEmail.isEmpty() && userPaswd.isEmpty())) {
                    mAuth.createUserWithEmailAndPassword(userEmail, userPaswd).addOnCompleteListener(loginactivity.this,
                            new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(loginactivity.this.getApplicationContext(),
                                        "SignUp unsuccessful: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(loginactivity.this, MainActivity.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(loginactivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = mEmailField.getText().toString();
                String userPaswd = mPasswordfField.getText().toString();
                if (userEmail.isEmpty()) {
                    mEmailField.setError("Provide your Email first!");
                    mEmailField.requestFocus();
                } else if (userPaswd.isEmpty()) {
                    mPasswordfField.setError("Enter Password!");
                    mPasswordfField.requestFocus();
                } else if (userEmail.isEmpty() && userPaswd.isEmpty()) {
                    Toast.makeText(loginactivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(userEmail.isEmpty() && userPaswd.isEmpty())) {
                    mAuth.signInWithEmailAndPassword(userEmail, userPaswd).addOnCompleteListener(loginactivity.this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(loginactivity.this, "Not sucessfull", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(loginactivity.this, MainActivity.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(loginactivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

}
