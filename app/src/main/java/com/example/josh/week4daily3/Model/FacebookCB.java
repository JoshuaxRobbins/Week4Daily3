package com.example.josh.week4daily3.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class FacebookCB implements FacebookCallback<LoginResult> {
    FirebaseAuth auth;
    Context context;
    public static final String TAG = "_TAG";

    public FacebookCB(Context context) {
        this.context = context;
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        handleFacebookToken(loginResult.getAccessToken());
        Toast.makeText(context, "Facebook Login Success", Toast.LENGTH_LONG).show();
    }

    private void handleFacebookToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        auth.signInWithCredential(credential);


    }

    @Override
    public void onCancel() {
        Toast.makeText(context, "Facebook Login Canceled", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onCancel: ");
    }

    @Override
    public void onError(FacebookException exception) {
        Toast.makeText(context, "Facebook Error", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onError: ");
    }
}
