package com.example.lean.movieapp.model_server.request;

public class SearchRequest {
    private int page;
    private String query;
    private String language = "en-US";
    private boolean include_adult = false;
}
