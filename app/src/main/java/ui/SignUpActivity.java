package ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.evans.quotwit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = SignUpActivity.class.getSimpleName();

    private FirebaseAuth mAuth;
    //firebase auth listener
    private FirebaseAuth.AuthStateListener mAuthListener;

    @BindView(R.id.et_user_name) EditText mUsername;
    @BindView(R.id.et_email) EditText mEmail;
    @BindView(R.id.et_password) EditText mPassword;
    @BindView(R.id.et_repeat_password)EditText mRepeatPassword;
    @BindView(R.id.loginTextView) TextView mLoginBtn;
    @BindView(R.id.bt_register) TextView createuser;

    final int MIN_PASSWORD_LENGTH = 6;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        mLoginBtn.setOnClickListener(this);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        viewInitializations();
        createAuthStateListener();
    }

    void viewInitializations(){


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //custom input validation > replace with isValidEmail();
    private boolean validateInput (){
        if (mUsername.getText().toString().equals("")){
            mUsername.setError("Please Choose Username");
            return false;
        }
        if (mEmail.getText().toString().isEmpty()){
            mEmail.setError("Please Enter Email address");
            return false;
        }
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailPattern).matches()){
            Toast.makeText(SignUpActivity.this, "email is valid", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (mPassword.getText().toString().isEmpty()|| mPassword.getText().toString().equals("")){
            mPassword.setError("Enter password");
            return false;
        }
        if (mPassword.getText().toString().length() < MIN_PASSWORD_LENGTH) {
            mPassword.setError("Password length must be min " + MIN_PASSWORD_LENGTH);
            return false;
        }
        if (!mPassword.getText().toString().equals(mRepeatPassword.getText().toString())) {
            mRepeatPassword.setError("Password does not match");
            return false;
        }
        return true;
    }


    public void submitSignup (View view) {
        if (validateInput()) {

            //send data to server
            createNewUser();

        }

    }
    // create new user method on click
    private void createNewUser() {
        final String username = mUsername.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String repeatPassword = mRepeatPassword.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Auth successful");
                    } else {
                        Toast.makeText(SignUpActivity.this, "Auth failed", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void createAuthStateListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Intent intent = new Intent(SignUpActivity.this, Topics.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    //LOGIN BTN INTENT
    @Override
    public void onClick(View view) {
        if (view == mLoginBtn){
            Intent toLogin = new Intent(SignUpActivity.this, LoginActivity.class);
            toLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(toLogin);
            finish();
        }
    }

    // REMOVE AUTH LISTENER
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}