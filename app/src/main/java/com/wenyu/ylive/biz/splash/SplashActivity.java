package com.wenyu.ylive.biz.splash;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.wenyu.ylive.R;
import com.wenyu.ylive.base.YLiveActivity;
import com.wenyu.ylive.biz.home.HomeActivity;
import com.wenyu.ylive.biz.home.dependency.DaggerHomeComponent;
import com.wenyu.ylive.biz.home.dependency.HomeComponent;
import com.wenyu.ylive.biz.home.dependency.HomeModule;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SplashActivity extends YLiveActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		HomeComponent homeComponent = DaggerHomeComponent.builder().homeModule(new HomeModule(this)).build();
		homeComponent.getId();
		Observable.timer(1, TimeUnit.SECONDS)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Action1<Long>() {
					@Override
					public void call(Long aLong) {
						startActivity(HomeActivity.newIntent(SplashActivity.this));
						finish();
					}
				});
	}

	@Override
	protected int contentId() {
		return R.layout.activity_splash;
	}
}
