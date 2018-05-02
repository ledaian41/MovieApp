package com.example.lean.movieapp.ui;

import android.content.Context;

public class UiUtils {

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }
}
