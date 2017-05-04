package com.wenyu.ylive.biz.live.view;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wenyu.mvp.view.BaseMvpView;
import com.wenyu.rtmp.camera.CameraListener;
import com.wenyu.rtmp.configuration.AudioConfiguration;
import com.wenyu.rtmp.configuration.CameraConfiguration;
import com.wenyu.rtmp.configuration.VideoConfiguration;
import com.wenyu.rtmp.stream.packer.rtmp.RtmpPacker;
import com.wenyu.rtmp.stream.sender.rtmp.RtmpSender;
import com.wenyu.rtmp.ui.CameraLivingView;
import com.wenyu.rtmp.utils.SopCastLog;
import com.wenyu.rtmp.video.effect.GrayEffect;
import com.wenyu.rtmp.video.effect.NullEffect;
import com.wenyu.ylive.BuildConfig;
import com.wenyu.ylive.R;
import com.wenyu.ylive.biz.live.presenter.LiveEventListener;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by chan on 17/5/2.
 */

public class LiveViewImpl extends BaseMvpView<LiveEventListener> implements ILiveView {

    private GrayEffect mGrayEffect;
    private NullEffect mNullEffect;

    @Bind(R.id.liveView)
    CameraLivingView mLFLiveView;

    @Bind(R.id.btnRecord)
    ImageButton mRecordBtn;

    @Bind(R.id.progressConnecting)
    ProgressBar mProgressConnecting;

    private GestureDetector mGestureDetector;
    private RtmpSender mRtmpSender;
    private VideoConfiguration mVideoConfiguration;
    private int mCurrentBps;

    private RtmpSender.OnSenderListener mSenderListener = new RtmpSender.OnSenderListener() {
        @Override
        public void onConnecting() {

        }

        @Override
        public void onConnected() {
            mProgressConnecting.setVisibility(View.GONE);
            mLFLiveView.start();
            mCurrentBps = mVideoConfiguration.maxBps;
        }

        @Override
        public void onDisConnected() {
            mProgressConnecting.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "fail to live", Toast.LENGTH_SHORT).show();
            //mRecordBtn.setBackgroundResource(R.mipmap.ic_record_start);
            mLFLiveView.stop();
        }

        @Override
        public void onPublishFail() {
            mProgressConnecting.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "fail to publish stream", Toast.LENGTH_SHORT).show();
            //mRecordBtn.setBackgroundResource(R.mipmap.ic_record_start);
        }

        @Override
        public void onNetGood() {
            if (mCurrentBps + 50 <= mVideoConfiguration.maxBps) {
                int bps = mCurrentBps + 50;
                if (mLFLiveView != null) {
                    boolean result = mLFLiveView.setVideoBps(bps);
                    if (result) {
                        mCurrentBps = bps;
                    }
                }
            }
        }

        @Override
        public void onNetBad() {
            if (mCurrentBps - 100 >= mVideoConfiguration.minBps) {
                int bps = mCurrentBps - 100;
                if (mLFLiveView != null) {
                    boolean result = mLFLiveView.setVideoBps(bps);
                    if (result) {
                        mCurrentBps = bps;
                    }
                }
            }
        }
    };

    @Override
    public void release() {
        mLFLiveView.stop();
        mLFLiveView.release();
    }

    @Override
    public void pause() {
        mLFLiveView.pause();
    }

    @Override
    public void resume() {
        mLFLiveView.resume();
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() - e2.getX() > 100
                    && Math.abs(velocityX) > 200) {
                // Fling left
                Toast.makeText(getActivity(), "Fling Left", Toast.LENGTH_SHORT).show();
            } else if (e2.getX() - e1.getX() > 100
                    && Math.abs(velocityX) > 200) {
                // Fling right
                Toast.makeText(getActivity(), "Fling Right", Toast.LENGTH_SHORT).show();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    @Inject
    public LiveViewImpl(@NonNull Activity activity) {
        super(activity);

        mGrayEffect = new GrayEffect(getActivity());
        mNullEffect = new NullEffect(getActivity());

        View root = activity.findViewById(R.id.live_container);
        ButterKnife.bind(this, root);

        SopCastLog.isOpen(true);
        mLFLiveView.init();
        CameraConfiguration.Builder cameraBuilder = new CameraConfiguration.Builder();
        cameraBuilder.setOrientation(CameraConfiguration.Orientation.LANDSCAPE)
                .setFacing(CameraConfiguration.Facing.BACK);
        CameraConfiguration cameraConfiguration = cameraBuilder.build();
        mLFLiveView.setCameraConfiguration(cameraConfiguration);

        VideoConfiguration.Builder videoBuilder = new VideoConfiguration.Builder();
        videoBuilder.setSize(640, 360);
        mVideoConfiguration = videoBuilder.build();
        mLFLiveView.setVideoConfiguration(mVideoConfiguration);

        //设置水印
//		Bitmap watermarkImg = BitmapFactory.decodeResource(getResources(), R.mipmap.watermark);
//		Watermark watermark = new Watermark(watermarkImg, 50, 25, WatermarkPosition.WATERMARK_ORIENTATION_BOTTOM_RIGHT, 8, 8);
//		mLFLiveView.setWatermark(watermark);

        //设置预览监听
        mLFLiveView.setCameraOpenListener(new CameraListener() {
            @Override
            public void onOpenSuccess() {
                Toast.makeText(getActivity(), "camera open success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onOpenFail(int error) {
                Toast.makeText(getActivity(), "camera open fail", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCameraChange() {
                Toast.makeText(getActivity(), "camera switch", Toast.LENGTH_LONG).show();
            }
        });

        //设置手势识别
        mGestureDetector = new GestureDetector(getActivity(), new GestureListener());
        mLFLiveView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return false;
            }
        });

        //初始化flv打包器
        RtmpPacker packer = new RtmpPacker();
        packer.initAudioParams(AudioConfiguration.DEFAULT_FREQUENCY, 16, false);
        mLFLiveView.setPacker(packer);

        //设置发送器
        mRtmpSender = new RtmpSender();
        mRtmpSender.setVideoParams(640, 360);
        mRtmpSender.setAudioParams(AudioConfiguration.DEFAULT_FREQUENCY, 16, false);
        mRtmpSender.setSenderListener(mSenderListener);
        mLFLiveView.setSender(mRtmpSender);
        mLFLiveView.setLivingStartListener(new CameraLivingView.LivingStartListener() {
            @Override
            public void startError(int error) {
                //直播失败
                Toast.makeText(getActivity(), "start living fail", Toast.LENGTH_SHORT).show();
                mLFLiveView.stop();
            }

            @Override
            public void startSuccess() {
                //直播成功
                Toast.makeText(getActivity(), "start living", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnCheckedChanged(R.id.live_start)
    void onLiveChecked(boolean checked) {
        if (checked) {
            //TODO
            String uploadUrl = BuildConfig.RTMP_BASE_URI;
            mRtmpSender.setAddress(uploadUrl);
            mProgressConnecting.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "开始推流", Toast.LENGTH_SHORT).show();
            mRtmpSender.connect();
        } else {
            mProgressConnecting.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "已经关播", Toast.LENGTH_SHORT).show();
            mLFLiveView.stop();
        }
    }
}