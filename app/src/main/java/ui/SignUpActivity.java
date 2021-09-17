package ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.evans.quotwit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.et_user_name) EditText mUsername;
    @BindView(R.id.et_email) EditText mEmail;
    @BindView(R.id.et_password) EditText mPassword;
    @BindView(R.id.et_repeat_password)EditText mRepeatPassword;
    @BindView(R.id.loginTextView) TextView mLoginBtn;

    final int MIN_PASSWORD_LENGTH = 6;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        mLoginBtn.setOnClickListener(this);
        viewInitializations();
    }

    void viewInitializations(){


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    boolean validateInput (){
        if (mUsername.getText().toString().equals("")){
            mUsername.setError("Please Choose Username");
            return false;
        }
        if (mEmail.getText().toString().isEmpty()){
            mEmail.setError("Please Enter Email address");
            return false;
        }
        if (mEmail.getText().toString().trim().matches(emailPattern)){
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

            String username = mUsername.getText().toString();
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            String repeatPassword = mRepeatPassword.getText().toString();

            Toast.makeText(SignUpActivity.this, "Account Created", Toast.LENGTH_SHORT).show();

            //call API BUT meanwhile ill add intent to login
            Intent login = new Intent(SignUpActivity.this, LoginActivity.class);
            login.putExtra("username", username);
            login.putExtra("password", password);
            startActivity(login);

        }

    }

    @Override
    public void onClick(View view) {
        if (view == mLoginBtn){
            Intent toLogin = new Intent(SignUpActivity.this, LoginActivity.class);
            toLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(toLogin);
            finish();
        }
    }
}