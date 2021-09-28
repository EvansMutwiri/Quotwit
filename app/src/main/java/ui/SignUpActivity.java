package ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.evans.quotwit.R;
import com.evans.quotwit.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity{

    public static final String TAG = SignUpActivity.class.getSimpleName();
    String username;

    private FirebaseAuth mAuth;

    //firebase auth listener
    FirebaseAuth.AuthStateListener mAuthListener;

    @BindView(R.id.et_user_name) EditText mNameEditText;
    @BindView(R.id.et_email) EditText mEmailEditText;
    @BindView(R.id.et_password) EditText mPasswordEditText;
    @BindView(R.id.et_repeat_password)EditText mConfirmPasswordEditText;
    @BindView(R.id.loginTextView) TextView mLoginBtn;
    @BindView(R.id.bt_register) Button btnRegister;

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
        createAuthStateListener();
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
        String username = mNameEditText.getText().toString().trim();
        final String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString();
        String repeatpass = mConfirmPasswordEditText.getText().toString();
//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

//        String validUsername = username;

        if(TextUtils.isEmpty(username)){
            mNameEditText.setError("Name cannot be blank");
            mNameEditText.requestFocus();
        }
        else if (TextUtils.isEmpty(email)){
            mEmailEditText.setError("Enter valid email");
            mEmailEditText.requestFocus();
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
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
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                hideProgressBar();

                if(task.isSuccessful()){
                    User user = new User(username, email);
                    createFirebaseUserProfile(Objects.requireNonNull(task.getResult().getUser()));

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
                            fUser.sendEmailVerification();
                            Toast.makeText(SignUpActivity.this, "Check your email to verify account", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            finish();
                        }
                    });
                } else {
                    Toast.makeText(SignUpActivity.this, "Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
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
                            Log.d(TAG, Objects.requireNonNull(user.getDisplayName()));
                            Toast.makeText(SignUpActivity.this, "The display name has ben set", Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }

    private void createAuthStateListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(SignUpActivity.this, Topics.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }

        };
    }
}