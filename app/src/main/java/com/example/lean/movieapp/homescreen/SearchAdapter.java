package com.example.lean.movieapp.homescreen;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lean.movieapp.R;
import com.example.lean.movieapp.model_server.response.MovieResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieResponse> mMovieList;

    SearchAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MovieResponse movieResponse = mMovieList.get(holder.getAdapterPosition());
        PopularViewHolder popularViewHolder = (PopularViewHolder) holder;
        String releaseYear;
        String[] releaseDate;
        if (movieResponse.getRelease_date() != null && !movieResponse.getRelease_date().isEmpty()) {
            releaseDate = movieResponse.getRelease_date().split("-");
            if (releaseDate.length != 0) {
                releaseYear = releaseDate[0];
            } else {
                releaseYear = "";
            }
        } else {
            releaseYear = "";
        }
        popularViewHolder.tvTitle.setText(movieResponse.getOriginal_title() + " (" + releaseYear + ")");
        Glide.with(popularViewHolder.itemView.getContext()).load(releaseYear).into(popularViewHolder.imgMovie);
    }

    @Override
    public int getItemCount() {
        return mMovieList == null ? 0 : mMovieList.size();
    }


    public void setMovies(List<MovieResponse> movieResponses) {
        this.mMovieList = movieResponses;
        notifyDataSetChanged();
    }

    public static class PopularViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.imgMovie)
        ImageView imgMovie;

        PopularViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
