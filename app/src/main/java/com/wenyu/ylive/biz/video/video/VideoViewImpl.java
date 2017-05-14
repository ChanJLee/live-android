package com.wenyu.ylive.biz.video.video;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.wenyu.danmuku.DanMaView;
import com.wenyu.danmuku.interfaces.IDanMa;
import com.wenyu.danmuku.type.TextDanMa;
import com.wenyu.media.cview.VideoView;
import com.wenyu.mvp.view.BaseMvpView;
import com.wenyu.ylive.R;
import com.wenyu.ylive.base.YLiveActivity;
import com.wenyu.ylive.biz.video.presenter.VideoEventListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chan on 17/4/24.
 */

public class VideoViewImpl extends BaseMvpView<VideoEventListener> implements IVideoView {

    @Bind(R.id.video_cover)
    ImageView mIvCover;

    @Bind(R.id.video_description)
    View mDescription;

    @Bind(R.id.video_overlay)
    View mOverlay;

    @Bind(R.id.video_view)
    VideoView mVideoView;

    @Bind(R.id.video_input)
    EditText mEtInput;

    @Bind(R.id.video_danma)
    DanMaView mDanMaView;

    private ViewGroup mRootView;

    private Data mData;

    private RequestManager mRequestManager;

    @Inject
    public VideoViewImpl(@NonNull Activity activity) {
        super(activity);

        mRequestManager = Glide.with(activity);

        mRootView = (ViewGroup) activity.findViewById(R.id.video_root);
        ButterKnife.bind(this, mRootView);

        YLiveActivity yLiveActivity = (YLiveActivity) activity;
        yLiveActivity.setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                Animator animator = ViewAnimationUtils.createCircularReveal(mDescription, 0, 0, 0, Math.max(mDescription.getWidth(), mDescription.getHeight()));
                animator.start();
            }
        });
    }

    @OnClick(R.id.video_play)
    void onPlayClicked(final View view) {
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        Resources resources = getActivity().getResources();
        float translationY = displayMetrics.heightPixels - resources.getDimension(R.dimen.height84);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0, -translationY);
        objectAnimator.setDuration(500);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mOverlay.setVisibility(View.VISIBLE);
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        Animator animator = ViewAnimationUtils.createCircularReveal(mOverlay, (int) (mOverlay.getWidth() - resources.getDimension(R.dimen.margin8)), mOverlay.getHeight() / 2, 0, mOverlay.getWidth());
        animatorSet.play(animator).after(objectAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mOverlay.setVisibility(View.INVISIBLE);
                mIvCover.setVisibility(View.INVISIBLE);
                view.setVisibility(View.INVISIBLE);
                play();
            }
        });
        animatorSet.start();
    }

    private void play() {
        mVideoView.setVideoURI(Uri.parse(mData.url));
        mVideoView.start();
    }

    @Override
    public void render(Data data) {
        mRequestManager.load(data.cover).into(mIvCover);
        YLiveActivity yLiveActivity = (YLiveActivity) getActivity();
        ActionBar actionBar = yLiveActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(data.title);
        }

        mData = data;
    }

    @Override
    public String getInput() {
        return mEtInput.getText().toString();
    }

    @Override
    public void clearInput() {
        mEtInput.setText("");
    }

    @Override
    public void renderDanma(String message) {
        TextDanMa text = new TextDanMa(message, mDanMaView.getWidth(), mDanMaView.getHeight() / 2, 16, IDanMa.RIGHT_TO_LEFT);
        text.setColor(Color.WHITE);
        mDanMaView.pushDanMa(text);
    }

    @OnClick(R.id.video_send)
    void onSendClicked() {
        if (getEventListener() != null) {
            getEventListener().onSendClicked();
        }
    }
}
