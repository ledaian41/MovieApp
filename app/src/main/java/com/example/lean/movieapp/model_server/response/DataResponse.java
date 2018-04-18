package com.example.lean.movieapp.model_server.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataResponse implements Parcelable {

    @Expose
    @SerializedName("results")
    private List<MovieResponse> results;
    @Expose
    @SerializedName("total_pages")
    private int total_pages;
    @Expose
    @SerializedName("total_results")
    private int total_results;
    @Expose
    @SerializedName("page")
    private int page;

    public List<MovieResponse> getResults() {
        return results;
    }

    public void setResults(List<MovieResponse> results) {
        this.results = results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.results);
        dest.writeInt(this.total_pages);
        dest.writeInt(this.total_results);
        dest.writeInt(this.page);
    }

    public DataResponse() {
    }

    protected DataResponse(Parcel in) {
        this.results = in.createTypedArrayList(MovieResponse.CREATOR);
        this.total_pages = in.readInt();
        this.total_results = in.readInt();
        this.page = in.readInt();
    }

    public static final Parcelable.Creator<DataResponse> CREATOR = new Parcelable.Creator<DataResponse>() {
        @Override
        public DataResponse createFromParcel(Parcel source) {
            return new DataResponse(source);
        }

        @Override
        public DataResponse[] newArray(int size) {
            return new DataResponse[size];
        }
    };
}
