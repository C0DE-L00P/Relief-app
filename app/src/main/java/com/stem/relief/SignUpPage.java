package com.stem.relief;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.stem.relief.LoginPage.user;
import static com.stem.relief.LoginPage.userID;

public class SignUpPage extends Activity {

    FirebaseDatabase database;
    DatabaseReference ref;

    private FirebaseAuth mAuth;

    EditText usernameSignUp;
    EditText passwordSignUp;
    EditText emailSignUp;
    EditText ageSignUp;

    ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("users");

        usernameSignUp = findViewById(R.id.username_signup_edit_text);
        passwordSignUp = findViewById(R.id.password_signup_edit_text);
        emailSignUp = findViewById(R.id.email_signup_edit_text);
        ageSignUp = findViewById(R.id.age_signup_edit_text);

        progressBar = findViewById(R.id.progress_bar);
    }

    public void registerUser() {

        progressBar.setVisibility(View.VISIBLE);

        String email = emailSignUp.getText().toString();

        if (email.isEmpty()) {
            emailSignUp.setError("Can't be blank");
            emailSignUp.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailSignUp.setError("Please, write a valid email");
            emailSignUp.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }


        if (usernameSignUp.getText().toString().isEmpty()) {
            usernameSignUp.setError("Can't be blank");
            usernameSignUp.requestFocus();
            return;
        }

        if (ageSignUp.getText().toString().isEmpty()) {
            ageSignUp.setError("Can't be blank");
            ageSignUp.requestFocus();
            return;
        }

        String password = passwordSignUp.getText().toString();

        if (password.length() < 6) {
            passwordSignUp.setError("Password can't be less than 6 chars");
            passwordSignUp.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            user = mAuth.getCurrentUser();
                            userID = mAuth.getCurrentUser().toString();

                            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(usernameSignUp.getText().toString())
                                    .build();

                            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.i("AuthStateJim", "username got saved");
                                        startActivity(new Intent(SignUpPage.this, MainScreen.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                        finish();
                                    } else {
                                        Log.i("AuthStateJim", "problem with saving username!!");
                                    }
                                }
                            });
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(SignUpPage.this, "you are already registered",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUpPage.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            Log.w("AuthStateJim", "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    public void SignUp(View view) {

        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            registerUser();
        } else {
            Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_SHORT).show();
        }
    }
}
