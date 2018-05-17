package com.example.lean.movieapp.homescreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lean.movieapp.R;
import com.example.lean.movieapp.common.Utils;
import com.example.lean.movieapp.model_server.response.MovieResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment {

    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvMovie)
    TextView tvMovie;

    private MovieResponse movieResponse;

    public static DetailFragment newInstance(MovieResponse movie) {

        Bundle args = new Bundle();
        args.putParcelable(Utils.Name.MOVIE, movie);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieResponse = getArguments().getParcelable(Utils.Name.MOVIE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_movie, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvMovie.setText(movieResponse.getTitle());
        tvDescription.setText(movieResponse.getOverview());
    }
}
