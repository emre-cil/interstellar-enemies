package com.example.interstellarenemies.init;

import android.content.Intent;
import android.os.*;
import android.view.*;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.interstellarenemies.FirebaseRealtimeUserIntegration;
import com.example.interstellarenemies.R;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;

        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth = FirebaseAuth.getInstance();



        //checks if user already sign in

        initGoogleSign();
    }

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

        Intent homePage = new Intent(this, HomePage.class);

        if (mAuth.getCurrentUser() != null) {
            homePage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            homePage.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(homePage);
            finish();
        }
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

                        FirebaseRealtimeUserIntegration.userAdd();
                        startActivity(homePage);
                    } else {
                        // If sign in fails, display a message to the user.
                        Snackbar.make(this.findViewById(android.R.id.content), task.getException().getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
    }
    //===========================================================
}