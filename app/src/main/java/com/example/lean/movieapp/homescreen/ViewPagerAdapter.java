package com.example.lean.movieapp.homescreen;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.widget.Toast;

import com.example.lean.movieapp.model_server.response.MovieResponse;


import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends SmartFragmentStatePagerAdapter {

    private List<MovieResponse> mMovieResponses;
    private List<CarouselFragment> carouselFragments;

    ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        carouselFragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return CarouselFragment.newInstance(mMovieResponses.get(position));
    }

    @Override
    public int getCount() {
        return mMovieResponses == null ? 0 : mMovieResponses.size();
    }

    public void addMovies(List<MovieResponse> movieResponses) {
        this.mMovieResponses = movieResponses;
        for (MovieResponse movie : movieResponses) {
            carouselFragments.add(CarouselFragment.newInstance(movie));
        }
        notifyDataSetChanged();
    }

    @Override
    public float getPageWidth(int position) {
        return 0.93f;
    }
}
