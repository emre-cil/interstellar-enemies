package com.example.interstellarenemies.init.mail;

import android.content.Intent;
import android.os.*;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.interstellarenemies.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassPage extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass_page);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;

        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth = FirebaseAuth.getInstance();
        Button resetButton = findViewById(R.id.changeUsernameFragmentBut);
        resetButton.setOnClickListener((View v) -> sendEmail());
    }

    //email reset password link operations.
    public void sendEmail() {
        String emailAddress = ((EditText) findViewById(R.id.resetEmailEdit)).getText().toString().trim();
        Intent signInPage = new Intent(this, SignInPage.class);

        mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Snackbar.make(this.findViewById(android.R.id.content),
                                getString(R.string.passResetLink), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        startActivity(signInPage);
                    } else
                        Snackbar.make(this.findViewById(android.R.id.content),
                                getString(R.string.emailNotFound), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                });
    }
}