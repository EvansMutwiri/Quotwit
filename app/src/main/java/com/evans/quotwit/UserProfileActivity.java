package com.evans.quotwit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import ui.Topics;

public class UserProfileActivity extends AppCompatActivity {

//    private static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAM_REQUEST_CODE = 102;
    private FirebaseUser fUser;
    private DatabaseReference databaseReference;
    private String userID;
    private ImageView profileEdit;

    String currentPhotoPath;


    BottomNavigationView bottomNavigationView;
    private CircleImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userID = fUser.getUid();

        final TextView welcomeText = (TextView) findViewById(R.id.welcome);
        final TextView profileName = (TextView) findViewById(R.id.user_prof_name);
        final TextView userEmail = (TextView) findViewById(R.id.user_email);
        profileImage = findViewById(R.id.profile_image);
        profileEdit = findViewById(R.id.edit_profile_image);

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                String name = userProfile.name;
                String email = userProfile.email;

                welcomeText.setText("Welcome");
                profileName.setText("Signed in as "+ name);
                userEmail.setText("Email " + email);
//                profileImage.setImageDrawable();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //bottom nav
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        //item selector listener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.saved:
                    startActivity(new Intent(getApplicationContext(), SavedContentActivity.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), Topics.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.profile:
                    return true;
            }
            return false;
        });

        profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open gallery
                openCamera();
            }
        });
    }

    ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                if (result.getResultCode() == RESULT_OK) {
                    Uri uri = result.getData().getData();
                    profileImage.setImageURI(uri);
                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                    Toast.makeText(this, "error" + ImagePicker.Companion.getError(result.getData()), Toast.LENGTH_SHORT).show();
                }
            });
    private void openCamera() {
        Toast.makeText(this, "Open Camera", Toast.LENGTH_SHORT).show();
        ImagePicker.Companion.with(UserProfileActivity.this)
                .crop()
                .cropOval()
                .maxResultSize(512, 512, true)
                .createIntentFromDialog((Function1) new Function1() {
                    public Object invoke(Object var1) {
                        this.invoke((Intent) var1);
                        return Unit.INSTANCE;
                    }
                    public final void invoke(@NotNull Intent it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        launcher.launch(it);
                    }
                });
    }
}