package com.example.lean.movieapp.model_server.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class SearchRequest implements Parcelable {
    @SerializedName("page")
    private int page;
    @SerializedName("query")
    private String query;
    @SerializedName("language")
    private String language = "en-US";
    @SerializedName("include_adult")
    private boolean include_adult = false;


    public SearchRequest() {
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Map<String, String> toQueryMap() {
        Map<String, String> map = new HashMap<>();
        if (query != null && !query.isEmpty()) {
            map.put("query", query);
        }
        map.put("page", String.valueOf(page));
        map.put("include_adult", "true");
        map.put("language", "en-US");

        return map;
    }

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

    protected SearchRequest(Parcel in) {
        this.page = in.readInt();
        this.query = in.readString();
        this.language = in.readString();
        this.include_adult = in.readByte() != 0;
    }

    public static final Creator<SearchRequest> CREATOR = new Creator<SearchRequest>() {
        @Override
        public SearchRequest createFromParcel(Parcel source) {
            return new SearchRequest(source);
        }

        @Override
        public SearchRequest[] newArray(int size) {
            return new SearchRequest[size];
        }
    };
}
