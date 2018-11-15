package com.example.josh.week4daily3.Model;

import android.app.Activity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class UserAuthenticator {


    private static final String TAG = UserAuthenticator.class.getSimpleName()+ "_TAG";
    FirebaseAuth auth;
    Callback callback;
    Activity activity;
    CompleteListener completeListener;
    GoogleSignInAccount account;

    public UserAuthenticator(Activity activity, CompleteListener completeListener) {
        auth = FirebaseAuth.getInstance();
        this.callback = (Callback) activity;
        this.activity = activity;
        this.completeListener = completeListener;
    }


    public void signInGoogle(GoogleSignInAccount account){
        completeListener.setType(0);
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(activity,completeListener);
    }

    public void signIn(String userEmail, String password) {

        completeListener.setType(0);
        auth.signInWithEmailAndPassword(userEmail, password)
                .addOnCompleteListener(activity, completeListener);


    }


    public void signUp(String userEmail, String password) {

        completeListener.setType(1);
        auth.createUserWithEmailAndPassword(userEmail, password)
                .addOnCompleteListener(activity,completeListener);
    }

    public void signOut() {

        auth.signOut();
    }


    public interface Callback{


        void onUserValidated(FirebaseUser user);


        void onUserInvalidated();
    }
}
