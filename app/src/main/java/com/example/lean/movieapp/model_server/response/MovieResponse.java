package com.example.lean.movieapp.model_server.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.lean.movieapp.api.APIManager;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse implements Parcelable {
    @Expose
    @SerializedName("release_date")
    private String release_date;
    @Expose
    @SerializedName("overview")
    private String overview;
    @Expose
    @SerializedName("adult")
    private boolean adult;
    @Expose
    @SerializedName("backdrop_path")
    private String backdrop_path;
    @Expose
    @SerializedName("genre_ids")
    private List<Integer> genre_ids;
    @Expose
    @SerializedName("original_title")
    private String original_title;
    @Expose
    @SerializedName("original_language")
    private String original_language;
    @Expose
    @SerializedName("poster_path")
    private String poster_path;
    @Expose
    @SerializedName("popularity")
    private double popularity;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("vote_average")
    private double vote_average;
    @Expose
    @SerializedName("video")
    private boolean video;
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("vote_count")
    private int vote_count;

    protected MovieResponse(Parcel in) {
        release_date = in.readString();
        overview = in.readString();
        adult = in.readByte() != 0;
        backdrop_path = in.readString();
        original_title = in.readString();
        original_language = in.readString();
        poster_path = in.readString();
        popularity = in.readDouble();
        title = in.readString();
        vote_average = in.readDouble();
        video = in.readByte() != 0;
        id = in.readInt();
        vote_count = in.readInt();
    }

    public static final Creator<MovieResponse> CREATOR = new Creator<MovieResponse>() {
        @Override
        public MovieResponse createFromParcel(Parcel in) {
            return new MovieResponse(in);
        }

        @Override
        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public boolean getAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return APIManager.BASE_IMAGE_URL + backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getPoster_path() {
        return APIManager.BASE_IMAGE_URL + poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public boolean getVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(release_date);
        dest.writeString(overview);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(backdrop_path);
        dest.writeString(original_title);
        dest.writeString(original_language);
        dest.writeString(poster_path);
        dest.writeDouble(popularity);
        dest.writeString(title);
        dest.writeDouble(vote_average);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeInt(id);
        dest.writeInt(vote_count);
    }
}
