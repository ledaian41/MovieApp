package com.example.lean.movieapp.login;

public interface LoginInterface {
    interface View {
        void initPresenter();
    }

    interface presenter {
        boolean isEmailValid(String email);

        boolean isPasswordValid(String password);
    }
}
