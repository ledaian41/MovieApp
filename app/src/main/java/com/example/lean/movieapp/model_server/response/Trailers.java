package com.example.lean.movieapp.model_server.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Trailers {

    @Expose
    @SerializedName("youtube")
    private List<Youtube> youtube;
    @Expose
    @SerializedName("quicktime")
    private List<String> quicktime;
    @Expose
    @SerializedName("id")
    private int id;

    public List<Youtube> getYoutube() {
        return youtube;
    }

    public List<String> getQuicktime() {
        return quicktime;
    }

    public int getId() {
        return id;
    }

    public static class Youtube {
        @Expose
        @SerializedName("type")
        private String type;
        @Expose
        @SerializedName("source")
        private String source;
        @Expose
        @SerializedName("size")
        private String size;
        @Expose
        @SerializedName("name")
        private String name;

        public String getType() {
            return type;
        }

        public String getSource() {
            return "https://www.youtube.com/watch?v=" + source;
        }

        public String getSize() {
            return size;
        }

        public String getName() {
            return name;
        }
    }
}
