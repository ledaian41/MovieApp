package com.example.lean.movieapp.login;

public class LoginPresenter implements LoginInterface.Prensenter {

    private LoginInterface.View view = null;

    @Override
    public void login() {

    }

    public void setView(LoginInterface.View view) {
        this.view = view;
    }

    public void onDestroy() {
        view = null;
    }
}
