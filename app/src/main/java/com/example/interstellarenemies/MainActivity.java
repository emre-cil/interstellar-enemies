package com.example.interstellarenemies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1337;
    private static FirebaseAuth mAuth;
    private static GoogleSignInClient mGoogleSignInClient;

    public static FirebaseAuth getmAuth() {
        return mAuth;
    }

    public static GoogleSignInClient getmGoogleSignInClient() {
        return mGoogleSignInClient;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        Intent homePage = new Intent(this, HomePage.class);
        homePage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homePage.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        //checks if user already sign in
        if (mAuth.getCurrentUser() != null) {
            startActivity(homePage);
            finish();
        }
        initGoogleSign();
    }

    /**
     * TODO: make fullscreen with no black bars.
     */
    @Override
    protected void onResume() {
        super.onResume();


        //Email signIn Image
        ImageView emailSignIn = findViewById(R.id.emailView);
        emailSignIn.setOnClickListener((View v) -> goToPage(SignInPage.class));

        //Google signIn Image
        ImageView googleSignIn = findViewById(R.id.googleSignImage);
        googleSignIn.setOnClickListener((View v) -> signInGoogle());

        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(SYSTEM_UI_FLAG_FULLSCREEN);
    }

    public void goToPage(Class<?> o) {
        Intent i = new Intent(this, o);
        startActivity(i);
    }

    //========================Google Sign========================
    private void initGoogleSign() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                //NOTE: User did not select an account so do nothing.
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        Intent homePage = new Intent(this, HomePage.class);
        homePage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homePage.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        startActivity(homePage);
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    //===========================================================
}