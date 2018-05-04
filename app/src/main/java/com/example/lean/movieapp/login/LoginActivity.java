package com.example.lean.movieapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lean.movieapp.homescreen.MainActivity;
import com.example.lean.movieapp.R;
import com.example.lean.movieapp.common.BaseActivity;
import com.example.lean.movieapp.common.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginInterface.View {
    private final String TAG = "LoginActivity";
    private LoginPresenter mLoginPresenter;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.tvMovie)
    TextView tvMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initPresenter();
        initView();
    }


    private void initView() {
        ButterKnife.bind(this);
        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.btnLoginGoogle).setOnClickListener(this);

        //TODO:ANIMATE tvMovie
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                doLoginWithEmailAndPassword();
                break;
            case R.id.btnLoginGoogle:
                doLoginWithGoogleAccount();
                break;
            default:
                break;
        }
    }

    private void doLoginWithGoogleAccount() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, Utils.CODE.LOGIN_GOOGLE_CODE);
    }

    private void doLoginWithEmailAndPassword() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if (mLoginPresenter.isEmailValid(email) && mLoginPresenter.isPasswordValid(password)) {
            mFireBaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnFailureListener(this, e -> Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_LONG).show())
                    .addOnSuccessListener(this, authResult -> {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_LONG).show();
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
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            //TODO nothing
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Utils.CODE.LOGIN_GOOGLE_CODE:
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    fireBaseAuthWithGoogle(account);
                } catch (ApiException e) {
                    Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void fireBaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFireBaseAuth.signInWithCredential(credential)
                .addOnFailureListener(this, e -> {
                    Log.w(TAG, "signInWithCredential:failure", e.getCause());
                    Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                })
                .addOnSuccessListener(this, authResult -> {
                    if (authResult != null) {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
    }
}
