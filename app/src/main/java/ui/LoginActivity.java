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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int PASSWORD_LENGTH = 6;
    EditText mUsername;
    EditText mPassword;
//    TextView mSignUp;
    @BindView(R.id.registerTextView) TextView mSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        mSignUp.setOnClickListener(this);
        viewInitializations();
    }

    void viewInitializations(){
        mUsername= findViewById(R.id.username);

        //set text from login intent
        Intent login = getIntent();
        String username = login.getStringExtra("username");
        mUsername.setText(username);

        mPassword= findViewById(R.id.password);

//        mSignUp = findViewById(R.id.registerTextView);
    }

    boolean validateInput() {

        if (mUsername.getText().toString().isEmpty()){
            mUsername.setError("Enter Username");
            return false;
        }
        if (mUsername.getText().toString().equals("")){
            mUsername.setError("Enter Username");
            return false;
        }
        if(mPassword.getText().toString().isEmpty() || mPassword.getText().toString().length()< PASSWORD_LENGTH){
            mPassword.setError("Enter valid password");
            return false;
        }
        return true;
    }

    public void viewTopics (View view) {
        if (validateInput()) {

            // Input is valid, here send data to your server

            String username = mUsername.getText().toString();
            String password = mPassword.getText().toString();

            Intent topics = new Intent(LoginActivity.this, Topics.class);
            topics.putExtra("username", username);

            startActivity(topics);

            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onClick(View view) {
        if(view == mSignUp) {
//            Toast.makeText(this, "sign upoppppp", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        }
    }
}