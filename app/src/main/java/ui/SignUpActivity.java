package ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity{

    public static final String TAG = SignUpActivity.class.getSimpleName();
    private String username;

    private FirebaseAuth mAuth;

    //firebase auth listener
    private FirebaseAuth.AuthStateListener mAuthListener;

    @BindView(R.id.et_user_name) EditText mNameEditText;
    @BindView(R.id.et_email) EditText mEmailEditText;
    @BindView(R.id.et_password) EditText mPasswordEditText;
    @BindView(R.id.et_repeat_password)EditText mConfirmPasswordEditText;
    @BindView(R.id.loginTextView) TextView mLoginBtn;
    @BindView(R.id.bt_register) Button btnRegister;
//    @BindView(R.id.firebaseProgressBar) ProgressBar mSignInProgressBar;
//    @BindView(R.id.loadingTextView) TextView mLoadingSignUp;

    @BindView(R.id.firebaseProgressBar) ProgressBar firebaseProgressBar;
    @BindView(R.id.loadingTextView) TextView loadingTextView;


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

    private void showProgressBar() {
        firebaseProgressBar.setVisibility(View.VISIBLE);
        loadingTextView.setVisibility(View.VISIBLE);
        loadingTextView.setText("Sign Up process in Progress");
    }

    private void hideProgressBar() {
        firebaseProgressBar.setVisibility(View.GONE);
        loadingTextView.setVisibility(View.GONE);
    }

    void viewInitializations(){

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void createNewUser() {
        username = mNameEditText.getText().toString().trim();
        String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString();
        String repeatpass = mConfirmPasswordEditText.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

//        String validUsername = username;

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
            showProgressBar();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    hideProgressBar();

                    if(task.isSuccessful()){
                        createFirebaseUserProfile(Objects.requireNonNull(task.getResult().getUser()));
                        Toast.makeText(SignUpActivity.this, "Account creation successfull", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, Topics.class));
                    } else {
                        Toast.makeText(SignUpActivity.this, "Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    private void createFirebaseUserProfile(final FirebaseUser user) {

        UserProfileChangeRequest addProfileName = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();

        user.updateProfile(addProfileName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
//                            Log.d(TAG, Objects.requireNonNull(user.getDisplayName()));
                            Toast.makeText(SignUpActivity.this, "The display name has ben set", Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }
}