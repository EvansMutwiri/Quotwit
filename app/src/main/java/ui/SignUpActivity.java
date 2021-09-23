package ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.evans.quotwit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity{

    public static final String TAG = SignUpActivity.class.getSimpleName();

    private FirebaseAuth mAuth;

    //firebase auth listener
    private FirebaseAuth.AuthStateListener mAuthListener;

    @BindView(R.id.et_user_name) EditText mNameEditText;
    @BindView(R.id.et_email) EditText mEmailEditText;
    @BindView(R.id.et_password) EditText mPasswordEditText;
    @BindView(R.id.et_repeat_password)EditText mConfirmPasswordEditText;
    @BindView(R.id.loginTextView) TextView mLoginBtn;
    @BindView(R.id.bt_register) Button btnRegister;
    @BindView(R.id.firebaseProgressBar) ProgressBar mSignInProgressBar;
    @BindView(R.id.loadingTextView) TextView mLoadingSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        viewInitializations();
        ButterKnife.bind(this);
        mLoginBtn.setOnClickListener(view -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        });

        //firebase
        mAuth = FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(view -> {
            createNewUser();
        });
    }

    void viewInitializations(){

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void createNewUser() {
        String username = mNameEditText.getText().toString().trim();
        String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString();
        String repeatpass = mConfirmPasswordEditText.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(TextUtils.isEmpty(username)){
            mNameEditText.setError("Name cannot be blank");
            mNameEditText.requestFocus();
        }
        else if (TextUtils.isEmpty(email)){
            mEmailEditText.setError("Enter valid email");
            mEmailEditText.requestFocus();
        }else if (!mEmailEditText.getText().toString().trim().matches(emailPattern)){
            mEmailEditText.setError("Enter valid email");
            mEmailEditText.requestFocus();
        }
        else if (TextUtils.isEmpty(password)){
            mPasswordEditText.setError("Enter password");
            mPasswordEditText.requestFocus();
        } else if (password.length() < 6) {
            mPasswordEditText.setError("Password must be 6 characters long");
        }else if (TextUtils.isEmpty(repeatpass)){
            mConfirmPasswordEditText.setError("Password must match");
            mConfirmPasswordEditText.requestFocus();
        }else if (!mConfirmPasswordEditText.getText().toString().matches(password)){
            mConfirmPasswordEditText.setError("Password must match");
            mConfirmPasswordEditText.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignUpActivity.this, "Account creation successfull", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(SignUpActivity.this, "Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}