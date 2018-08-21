package com.udacity.nanodegree.popularmovies.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class LayoutUtils {

    public static int getSpanCount(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }
}
