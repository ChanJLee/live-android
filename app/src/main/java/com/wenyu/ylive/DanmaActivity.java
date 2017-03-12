package com.wenyu.ylive;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.EditText;

import com.wenyu.danmuku.DanMaView;
import com.wenyu.danmuku.interfaces.IDanMa;
import com.wenyu.danmuku.type.TextDanMa;

public class DanmaActivity extends AppCompatActivity {

	private EditText mEditText;
	private DanMaView mDanMaView;
	private boolean mHasInit = false;
	private int mWidth;
	private int mHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_danma);

		mEditText = (EditText) findViewById(R.id.content);
		findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mHasInit) {
					String content = mEditText.getText().toString();
					if (TextUtils.isEmpty(content)) {
						return;
					}

					mDanMaView.pushDanMa(new TextDanMa(content, mWidth, mHeight / 2, 10, IDanMa.RIGHT_TO_LEFT));
				}
			}
		});

		mDanMaView = (DanMaView) findViewById(R.id.danma);
		mDanMaView.getHolder().addCallback(new SurfaceHolder.Callback() {
			@Override
			public void surfaceCreated(SurfaceHolder holder) {

			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
				mHasInit = true;
				mWidth = width;
				mHeight = height;
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				mHasInit = false;
			}
		});
	}

	public static Intent newIntent(Context context) {
		return new Intent(context, DanmaActivity.class);
	}
}
