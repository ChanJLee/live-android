package com.wenyu.base;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.MenuItem;

/**
 * Created by jiacheng.li on 17/1/11.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class BizActivity extends BaseActivity {

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		if (isTaskRoot()) {
			home();
		}
		finish();
	}

	public void home() {
		try {
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.setAction("com.wenyu.backhome");
			intent.setData(Uri.parse("ylive://" + getPackageName()));
			startActivity(intent);
			Log.d("", "task root, go home by intent");
		} catch (Exception e) {
		}
	}
}
