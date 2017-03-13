package com.wenyu.media.media;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.MediaController;

import java.util.ArrayList;
import java.util.List;

public class AndroidMediaController extends MediaController {
    private ActionBar mActionBar;
    private List<View> mShowOnceArray = new ArrayList<View>();

    public AndroidMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSupportActionBar(@Nullable ActionBar actionBar) {
        mActionBar = actionBar;
        if (mActionBar == null) {
            return;
        }
        
        if (isShowing()) {
            mActionBar.show();
        } else {
            mActionBar.hide();
        }
    }

    @Override
    public void show() {
        super.show();
        if (mActionBar != null)
            mActionBar.show();
    }

    @Override
    public void hide() {
        super.hide();
        if (mActionBar != null)
            mActionBar.hide();
        for (View view : mShowOnceArray)
            view.setVisibility(View.GONE);
        mShowOnceArray.clear();
    }

    public void showOnce(@NonNull View view) {
        mShowOnceArray.add(view);
        view.setVisibility(View.VISIBLE);
        show();
    }
}
