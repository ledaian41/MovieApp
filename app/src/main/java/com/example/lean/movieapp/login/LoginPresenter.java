package com.example.lean.movieapp.login;

public class LoginPresenter implements LoginInterface.presenter {
    private LoginInterface.View view;

    public LoginPresenter(LoginInterface.View view) {
        this.view = view;
    }

    public void onDestroy() {
        this.view = null;
    }

    @Override
    public boolean isEmailValid(String email) {
        String emailPattern = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}";
        return email.matches(emailPattern);
    }

    @Override
    public boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}
