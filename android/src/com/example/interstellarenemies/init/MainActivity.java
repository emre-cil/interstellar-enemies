package com.example.interstellarenemies.init;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.*;
import android.view.*;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.interstellarenemies.FirebaseRealtimeUserIntegration;
import com.example.interstellarenemies.LoadingPage;
import com.example.interstellarenemies.R;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1337;
    private static FirebaseAuth mAuth;
    private static GoogleSignInClient mGoogleSignInClient;
    private String dbVersion, version;

    public static FirebaseAuth getmAuth() {
        return mAuth;
    }

    public static GoogleSignInClient getmGoogleSignInClient() {
        return mGoogleSignInClient;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //assign version from gradle.
        try {
            version = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;

        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth = FirebaseAuth.getInstance();
        initGoogleSign();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isAlreadySign();
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

    //controls if the user already sign the game.
    private void isAlreadySign() {
        if (mAuth.getCurrentUser() != null) {
            Intent loadingPage = new Intent(this, LoadingPage.class);
            startActivity(loadingPage);
        }
        Intent homePage = new Intent(this, HomePage.class);
        //get version
        DatabaseReference keyRef = FirebaseDatabase.getInstance().getReference("version");
        keyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snap) {
                dbVersion = snap.getValue().toString();
                //check if it is already login and version of app.
                if (version.equals(dbVersion)) {
                    if (mAuth.getCurrentUser() != null) {
                        homePage.setFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homePage);
                        finish();
                    }
                    imageFunctions();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    //set images functions
    private void imageFunctions() {
        //Email signIn Image
        ImageView emailSignIn = findViewById(R.id.emailView);
        emailSignIn.setOnClickListener((View v) -> goToPage(SignInPage.class));

        //Google signIn Image
        ImageView googleSignIn = findViewById(R.id.googleSignImage);
        googleSignIn.setOnClickListener((View v) -> signInGoogle());

        View view = getWindow().getDecorView();
        view.setSystemUiVisibility(SYSTEM_UI_FLAG_FULLSCREEN);
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
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference keyRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
                        keyRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshotInside) {
                                if (snapshotInside.child("name").getValue() == null) {
                                    FirebaseRealtimeUserIntegration.userAdd();
                                }
                                startActivity(homePage);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });


                    } else {
                        // If sign in fails, display a message to the user.
                        Snackbar.make(this.findViewById(android.R.id.content), task.getException().getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
    }

}