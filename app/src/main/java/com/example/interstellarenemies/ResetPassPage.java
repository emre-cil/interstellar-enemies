package com.example.interstellarenemies;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.google.android.gms.tasks.*;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassPage extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass_page);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth = FirebaseAuth.getInstance();
        Button resetButton = findViewById(R.id.resetPassBut);
        resetButton.setOnClickListener((View v) -> sendEmail());
    }

    public void sendEmail() {
        String emailAddress = ((EditText) findViewById(R.id.resetEmailEdit)).getText().toString().trim();
        Intent signInPage = new Intent(this, SignInPage.class);

        mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPassPage.this, "Password reset link sent your email address", Toast.LENGTH_SHORT).show();
                            startActivity(signInPage);
                        } else
                            Toast.makeText(ResetPassPage.this, "Email address not found.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
