package com.example.lean.movieapp.homescreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.lean.movieapp.model_server.response.MovieResponse;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<MovieResponse> mMovieResponses;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
