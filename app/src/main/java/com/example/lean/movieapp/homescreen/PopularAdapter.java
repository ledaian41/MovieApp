package com.example.lean.movieapp.homescreen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lean.movieapp.R;
import com.example.lean.movieapp.model_server.response.MovieResponse;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieResponse> mMovieList;
    private Context context;

    public PopularAdapter(List<MovieResponse> mMovieList, Context context) {
        this.mMovieList = mMovieList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MovieResponse movieResponse = mMovieList.get(holder.getAdapterPosition());
        PopularViewHolder popularViewHolder = (PopularViewHolder) holder;
        String image = movieResponse.getPoster_path();
        Glide.with(context).load(movieResponse.getPoster_path()).into(popularViewHolder.imgMovie);
    }

    @Override
    public int getItemCount() {
        return mMovieList == null ? 0 : mMovieList.size();
    }

    public void onDestroy() {
        context = null;
    }

    public void setMovieResponses(List<MovieResponse> movieResponses) {
        this.mMovieList = movieResponses;
        notifyDataSetChanged();
    }

    private static class PopularViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMovie;

        public PopularViewHolder(View itemView) {
            super(itemView);
            imgMovie = itemView.findViewById(R.id.imgMovie);
        }
    }
}
