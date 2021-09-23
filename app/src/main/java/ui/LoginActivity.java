package ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.evans.quotwit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity{
    private static final int PASSWORD_LENGTH = 6;
    EditText mEmailTv;
    EditText mPassword;
    Button mLoginBtn;
    TextView mSignUp;

    //firebase
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailTv = findViewById(R.id.email_tv);
        mPassword = findViewById(R.id.password);
        mLoginBtn = findViewById(R.id.bt_login);
        mSignUp = findViewById(R.id.registerTextView);

        mAuth = FirebaseAuth.getInstance();

        mLoginBtn.setOnClickListener(view -> {
            loginUser();
        });

        mSignUp.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });
    }

    private void loginUser(){
        String email = mEmailTv.getText().toString().trim();
        String password = mPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            mEmailTv.setError("Enter valid email");
            mEmailTv.requestFocus();
        } else if(TextUtils.isEmpty(password)){
            mPassword.setError("Enter password");
            mPassword.requestFocus();
        }else if(password.length()< 6 ){
            mPassword.setError("Password must be more than 6 characters");
            mPassword.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Login successfull", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, Topics.class));

                    }else {
                        Toast.makeText(LoginActivity.this, "ERROR: " +task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}