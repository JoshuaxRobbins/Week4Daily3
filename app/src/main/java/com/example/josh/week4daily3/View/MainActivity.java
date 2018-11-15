package com.example.josh.week4daily3.View;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.josh.week4daily3.Model.CompleteListener;
import com.example.josh.week4daily3.Model.FacebookCB;
import com.example.josh.week4daily3.R;
import com.example.josh.week4daily3.Model.UserAuthenticator;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class MainActivity extends AppCompatActivity implements UserAuthenticator.Callback {

    private CallbackManager callbackManager;
    public static final String TAG = "_TAG";
    private EditText etPassword;
    private EditText etEmail;

    UserAuthenticator userAuthenticator;
    GoogleSignInClient googleSignInClient;
    TwitterLoginButton twitterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);


        userAuthenticator = new UserAuthenticator(this, new CompleteListener(this));
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("2LQiIcS5Hukw2Px32syl6IOxv",
                        "mxT7kV6Zp0yk5Kr4toa0Lpje17PUogYEgch4lnpm9FRXpxtL9Z"))
                .debug(true)
                .build();
        Twitter.initialize(config);
        twitterButton = (TwitterLoginButton) findViewById(R.id.btnTwitter);
        twitterButton.setCallback(new com.twitter.sdk.android.core.Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Log.d(TAG, "success: ");
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d(TAG, "failure: ");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 101) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                userAuthenticator.signInGoogle(account);
                Log.d(TAG, "onActivityResult: ");
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
        twitterButton.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onUserValidated(FirebaseUser user) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserInvalidated() {
        Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();

    }

    public void onSignUp(View view) {

        userAuthenticator.signUp(etEmail.getText().toString(),
                etPassword.getText().toString());
    }

    public void onSignIn(View view) {

        userAuthenticator.signIn(etEmail.getText().toString(),
                etPassword.getText().toString());
    }

    public void onFacebook(View view) {
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCB(getApplicationContext()));

    }

    public void googleSignIn(View view) {
        Log.d(TAG, "googleSignIn: ");
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
    }

    public void googleSignOut(View view) {
        userAuthenticator.signOut();
    }


}
