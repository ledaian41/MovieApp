package com.example.lean.movieapp.homescreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.lean.movieapp.model_server.response.MovieResponse;

import java.util.List;

public class ViewPagerAdapter extends SmartFragmentStatePagerAdapter {

    private List<MovieResponse> mMovieResponses;

    ViewPagerAdapter(FragmentManager fm, List<MovieResponse> responses) {
        super(fm);
        this.mMovieResponses = responses;
    }

    @Override
    public Fragment getItem(int position) {
        MovieResponse movie = mMovieResponses.get(position);
        return CarouselFragment.newInstance(movie);
    }

    @Override
    public int getCount() {
        return mMovieResponses == null ? 0 : mMovieResponses.size();
    }

    public void addMovies(List<MovieResponse> movieResponses) {
        this.mMovieResponses = movieResponses;
        notifyDataSetChanged();
    }
}
