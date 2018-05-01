package com.example.lean.movieapp.homescreen;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.example.lean.movieapp.model_server.response.MovieResponse;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ViewPagerAdapter extends SmartFragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {

    private List<MovieResponse> mMovieResponses;
    private Context mContext;

    ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Toast.makeText(mContext, "onPageScrolled: " + String.valueOf(position), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageSelected(int position) {
        Toast.makeText(mContext, "onPageSelected" + String.valueOf(position), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Toast.makeText(mContext, "onPageScrollStateChanged" + String.valueOf(state), Toast.LENGTH_SHORT).show();
    }
}
