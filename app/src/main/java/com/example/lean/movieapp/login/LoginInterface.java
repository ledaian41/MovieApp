package com.example.lean.movieapp.login;

public interface LoginInterface {
    interface View {
        void initPresenter();

        void loginSuccess();

        void loginFail();
    }

    interface Prensenter {
        void login();
    }

}
