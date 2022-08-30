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
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends Activity {

    public static FirebaseUser user;

    public static String userID;
    EditText emailLoginText;
    EditText passwordLoginText;
    
    ProgressBar progressBar;

    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();

        emailLoginText = findViewById(R.id.username_edit_text);
        passwordLoginText = findViewById(R.id.password_edit_text);
        progressBar = findViewById(R.id.progress_bar_login);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginPage.this,MainScreen.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }
    }


    public void Login(View view){

        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            loginMethod();
        } else {
            Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void loginMethod(){

        progressBar.setVisibility(View.VISIBLE);

        String email = emailLoginText.getText().toString();

        if (email.isEmpty()){
            emailLoginText.setError("Can't be blank");
            emailLoginText.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailLoginText.setError("Please, write a valid email");
            emailLoginText.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }

        String password = passwordLoginText.getText().toString();

        if (password.length()< 6){
            passwordLoginText.setError("Password can't be less than 6 chars");
            passwordLoginText.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }
        
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("AuthStateJim", "signInWithEmail:success");
                            finish();

                            userID = mAuth.getCurrentUser().toString();
                            startActivity(new Intent(LoginPage.this,MainScreen.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AuthStateJim", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginPage.this, "wrong E-mail/Password or Connection error",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void SignUp(View view){
        startActivity(new Intent(LoginPage.this,SignUpPage.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}
