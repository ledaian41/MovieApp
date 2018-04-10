package com.example.lean.movieapp.common;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BaseActivity extends AppCompatActivity {
    protected FirebaseAuth mFireBaseAuth;
    protected FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFireBaseAuth = FirebaseAuth.getInstance();
    }

    public boolean isLogin() {
        mCurrentUser = mFireBaseAuth.getCurrentUser();
        return mCurrentUser != null;

    }
}
