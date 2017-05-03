package com.wenyu.ylive.biz.live;

import android.os.Bundle;

import com.wenyu.ylive.R;
import com.wenyu.ylive.base.YLiveActivity;

public class LiveActivity extends YLiveActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int contentId() {
        return R.layout.activity_live;
    }


//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mLFLiveView.pause();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mLFLiveView.resume();
//    }

    //TODO

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mLFLiveView.stop();
//        mLFLiveView.release();
//    }
}
