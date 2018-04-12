package com.example.lean.movieapp.homescreen;

public class MainInterface {
    interface View {
        void getTopRatedSuccess();

        void getPopularSuccess();
    }

    interface presenter {
        void getTopRated(int page);

        void getPopular(int page);
    }
}
