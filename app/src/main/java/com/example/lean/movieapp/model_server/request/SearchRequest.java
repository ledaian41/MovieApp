package com.example.lean.movieapp.model_server.request;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class SearchRequest implements Parcelable {
    private int page;
    private String query;
    private String language = "en-US";
    private boolean include_adult = false;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeString(this.query);
        dest.writeString(this.language);
        dest.writeByte(this.include_adult ? (byte) 1 : (byte) 0);
    }

    public SearchRequest() {
    }

    protected SearchRequest(Parcel in) {
        this.page = in.readInt();
        this.query = in.readString();
        this.language = in.readString();
        this.include_adult = in.readByte() != 0;
    }

    public static final Parcelable.Creator<SearchRequest> CREATOR = new Parcelable.Creator<SearchRequest>() {
        @Override
        public SearchRequest createFromParcel(Parcel source) {
            return new SearchRequest(source);
        }

        @Override
        public SearchRequest[] newArray(int size) {
            return new SearchRequest[size];
        }
    };

    public void setPage(int page) {
        this.page = page;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setInclude_adult(boolean include_adult) {
        this.include_adult = include_adult;
    }

    public Map<String, String> toQueryMap() {
        Map<String, String> map = new HashMap<>();
        if (query != null && !query.trim().isEmpty()) map.put("query", query);
        map.put("page", String.valueOf(page));
        map.put("include_adult", "true");
        map.put("language", "en-US");

        return map;
    }
}
