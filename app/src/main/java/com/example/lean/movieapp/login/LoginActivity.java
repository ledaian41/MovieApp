package com.example.lean.movieapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lean.movieapp.MainActivity;
import com.example.lean.movieapp.R;
import com.example.lean.movieapp.common.BaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginInterface.View {
    private LoginPresenter mLoginPresenter;
    private EditText edtEmail;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initPresenter();
        initView();
    }

    private void initView() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        findViewById(R.id.btnLogin).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                doLoginWithEmailAndPassword();
                break;
            default:
                break;
        }
    }

    private void doLoginWithEmailAndPassword() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if (mLoginPresenter.isEmailValid(email) && mLoginPresenter.isPasswordValid(password)) {
            mFireBaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Please input correct email and password", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void initPresenter() {
        mLoginPresenter = new LoginPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isLogin()) {
            //Return true => User has been logged
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } else {
            //TODO nothing
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.onDestroy();
    }
}
