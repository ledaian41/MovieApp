package com.example.lean.movieapp.homescreen;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.widget.Toast;

import com.example.lean.movieapp.model_server.response.MovieResponse;


import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends SmartFragmentStatePagerAdapter implements CardAdapter {

    private List<MovieResponse> mMovieResponses;
    private float baseElevation;
    private List<CarouselFragment> carouselFragments;

    ViewPagerAdapter(FragmentManager fm, float baseElevation) {
        super(fm);
        carouselFragments = new ArrayList<>();
        this.baseElevation = baseElevation;
    }

    @Override
    public Fragment getItem(int position) {
        return CarouselFragment.newInstance(mMovieResponses.get(position));
    }

    @Override
    public float getBaseElevation() {
        return baseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return carouselFragments.get(position).getCardView();
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

}
