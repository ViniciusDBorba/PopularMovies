package com.udacity.nanodegree.popularmovies.ui.components;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class SaveInstanceRecyclerView extends RecyclerView {

    private static String LAYOUT_MANAGER = "LAYOUT_MANAGER";
    private static String SUPER_STATE = "SUPER_STATE";

    private Parcelable layoutManagerState;

    public SaveInstanceRecyclerView(@NonNull Context context) {
        super(context);
    }

    public SaveInstanceRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SaveInstanceRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);
        restoreScroll();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundleState = new Bundle();
        bundleState.putParcelable(LAYOUT_MANAGER, this.getLayoutManager().onSaveInstanceState());
        bundleState.putParcelable(SUPER_STATE, super.onSaveInstanceState());

        return bundleState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundleState = (Bundle) state;
            layoutManagerState = bundleState.getParcelable(LAYOUT_MANAGER);
            state = bundleState.getParcelable(SUPER_STATE);
        }

        super.onRestoreInstanceState(state);
    }

    public void restoreScroll() {
        if (layoutManagerState != null) {
            this.getLayoutManager().onRestoreInstanceState(layoutManagerState);
            layoutManagerState = null;
        }
    }


}
