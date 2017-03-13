package com.wenyu.ylive.test;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wenyu.xmpp.XmppClient;
import com.wenyu.ylive.R;

import org.jivesoftware.smack.XMPPException;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class XMPPActivity extends AppCompatActivity {

	private EditText mEditText;
	private Button mSend;
	private TextView mContent;
	private XmppClient mXmppClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xmpp);

		mEditText = (EditText) findViewById(R.id.input);
		mSend = (Button) findViewById(R.id.send);
		mContent = (TextView) findViewById(R.id.content);

		mXmppClient = XmppClient.getXmppClient(getApplicationContext(), "ylive", "19940525");
		try {
			mXmppClient.enterRoom("spark_name@conference.192.168.1.101",
					new XmppClient.Callback() {
						@Override
						public void onMessageReceived(boolean isFromMe, String message) {
							mContent.setText(message);
						}
					})
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(new Subscriber<Void>() {
						@Override
						public void onCompleted() {
							Log.d("chan_debug", "xmpp complete");
						}

						@Override
						public void onError(Throwable e) {
							Log.d("chan_debug", "xmpp error");
						}

						@Override
						public void onNext(Void aVoid) {
							Log.d("chan_debug", "xmpp next");
						}
					});
		} catch (XMPPException e) {
			e.printStackTrace();
		}

		mSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String content = mEditText.getText().toString();
				if (!TextUtils.isEmpty(content)) {
					mXmppClient.sendDanma(content)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(new Subscriber<Void>() {
						@Override
						public void onCompleted() {
							Log.d("chan_debug", "send complete");
						}

						@Override
						public void onError(Throwable e) {
							Log.d("chan_debug", "send error");
						}

						@Override
						public void onNext(Void aVoid) {
							Log.d("chan_debug", "send next");
						}
					});
				}
			}
		});
	}

	public static Intent newIntent(Context context) {
		return new Intent(context, XMPPActivity.class);
	}
}
