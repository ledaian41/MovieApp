package com.example.lean.movieapp.homescreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lean.movieapp.R;
import com.example.lean.movieapp.common.Utils;
import com.example.lean.movieapp.model_server.response.MovieResponse;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarouselFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.cardView)
    CardView cardView;
    @BindView(R.id.imgMovie)
    ImageView imgMovie;
    private MovieResponse mMovie;

    public static CarouselFragment newInstance(MovieResponse movie) {
        Bundle args = new Bundle();
        args.putParcelable(Utils.Name.MOVIE, movie);
        EventBus.getDefault().post(new ViewPagerEvent(movie));
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
        View view = inflater.inflate(R.layout.fragment_carousel, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cardView.setMaxCardElevation(cardView.getCardElevation() * CardAdapter.MAX_ELEVATION_FACTOR);
        imgMovie.setOnClickListener(this);
        Glide.with(view.getContext()).load(mMovie.getPoster_path()).into(imgMovie);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgMovie:
                Toast.makeText(getContext(), mMovie.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    public CardView getCardView() {
        return cardView;
    }

    public static class ViewPagerEvent {
        private MovieResponse movieResponse;

        ViewPagerEvent(MovieResponse movieResponse) {
            this.movieResponse = movieResponse;
        }

        public MovieResponse getMovieResponse() {
            return movieResponse;
        }
    }
}
