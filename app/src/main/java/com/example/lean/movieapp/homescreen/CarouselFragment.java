package com.example.lean.movieapp.homescreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lean.movieapp.R;
import com.example.lean.movieapp.common.Utils;
import com.example.lean.movieapp.model_server.response.MovieResponse;

public class CarouselFragment extends Fragment {
    private MovieResponse mMovie;

    public static CarouselFragment newInstance(MovieResponse movie) {

        Bundle args = new Bundle();
        args.putParcelable(Utils.Name.MOVIE, movie);

        CarouselFragment fragment = new CarouselFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(Utils.Name.MOVIE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carousel, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imgMovie = view.findViewById(R.id.imgMovie);
        Glide.with(view.getContext()).load(mMovie.getPoster_path()).into(imgMovie);
    }
}
