package com.wenyu.ylive.base;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wenyu.ylive.R;


public class YLiveProgressDialog extends Dialog {

	private Context mContext;
	private TextView mTvMessage;

	public YLiveProgressDialog(Context context) {
		super(context, R.style.YLiveBase_ProgressDialog);
		this.mContext = context;
		this.setContentView(R.layout.common_shanbay_progress_dialog);
		this.getWindow().getAttributes().gravity = Gravity.CENTER;

		mTvMessage = (TextView) findViewById(R.id.msg);
	}

	private void setContentMessage(String msg) {
		if (msg == null || msg.length() <= 0) {
			return;
		}
		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mTvMessage.getLayoutParams();
		lp.leftMargin = (int) mContext.getResources().getDimension(R.dimen.margin5);
		mTvMessage.setText(msg);
	}

	public void showProgressDialog() {
		showProgressDialog(null);
	}

	public void showProgressDialog(String text) {
		if (!TextUtils.isEmpty(text)) {
			setContentMessage(text);
		}

		if (!isShowing()) {
			setCanceledOnTouchOutside(false);
			setCancelable(true);
			show();
		}
	}

	public void dismissProgressDialog() {
		dismiss();
	}

}
