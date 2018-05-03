package com.example.lean.movieapp.ui;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class MyTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 1f;
    private static final float MIN_ALPHA = 0.85f;

    @Override
    public void transformPage(@NonNull View view, float position) {
        float scale = Math.abs(Math.abs(position) - 1);
        float factor;
        if (scale <= 0.85f) {
            factor = MIN_ALPHA;
        } else {
            factor = scale;
        }
        view.setScaleY(factor);
        view.setScaleX(factor);
    }
}
