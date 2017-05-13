package com.wenyu.xmpp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.muc.MultiUserChat;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by jiacheng.li on 17/3/8.
 * Copyright © 2016年 扇贝网(shanbay.com).
 * All rights reserved.
 */
public class XmppClient {
	private volatile static XmppClient sXmppClient;

	private Context mContext;

	private XMPPConnection mXMPPConnection;

	private String mCurrentRoom;
	public String mPassword;
	public String mUsername;

	private MultiUserChat mMultiUserChat;
	private Callback mCallback;

	private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(android.os.Message msg) {
			if (mCallback == null) {
				return;
			}

			if (msg.obj instanceof Packet) {
				Packet packet = (Packet) msg.obj;
				Message message = (Message) packet;
				mCallback.onMessageReceived(
						TextUtils.equals(packet.getFrom(),
						String.format("%s/%s", mCurrentRoom, mUsername)),
						message.getBody());
			}
		}
	};

	private PacketListener mPacketListener = new PacketListener() {
		@Override
		public void processPacket(Packet packet) {
			if (mCallback == null) {
				return;
			}

			android.os.Message msg = new android.os.Message();
			msg.obj = packet;
			mMainHandler.sendMessage(msg);
		}
	};

	private XmppClient(@NonNull Context context, @NonNull String username, @NonNull String password) {
		mPassword = password;
		mUsername = username;
		mContext = context;
	}

	public static XmppClient getXmppClient(Context appContext, String username, String password) {
		if (sXmppClient == null) {
			synchronized (XmppClient.class) {
				if (sXmppClient == null) {
					sXmppClient = new XmppClient(appContext, username, password);
				}
			}
		}
		return sXmppClient;
	}

	public Observable<Void> login() {
		return Observable.create(new Observable.OnSubscribe<Void>() {
			@Override
			public void call(Subscriber<? super Void> subscriber) {
				try {
					loginAccount();
					subscriber.onCompleted();
				} catch (XMPPException e) {
					subscriber.onError(e);
				}
			}
		});
	}

	private synchronized void loginAccount() throws XMPPException {
		if (mXMPPConnection != null && mXMPPConnection.isConnected() && !mXMPPConnection.isAnonymous()) {
			return;
		}

		if (mXMPPConnection == null) {
			ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration(BuildConfig.XMPP_URI, BuildConfig.XMPP_PORT);
			mXMPPConnection = new XMPPConnection(connectionConfiguration);
		}

		if (!mXMPPConnection.isConnected() || mXMPPConnection.isAnonymous()) {
			mXMPPConnection.connect();
			mXMPPConnection.login(mUsername, mPassword);
			Presence presence = new Presence(Presence.Type.available);
			mXMPPConnection.sendPacket(presence);
		}
	}

	public Observable<Void> logout() {
		return Observable.create(new Observable.OnSubscribe<Void>() {
			@Override
			public void call(Subscriber<? super Void> subscriber) {

			}
		});
	}

	public synchronized void logoutAccount() {
		if (mXMPPConnection != null && mXMPPConnection.isConnected() && !mXMPPConnection.isAnonymous()) {
			mXMPPConnection.disconnect();
		}
	}

	public Observable<Void> enterRoom(final String room, final Callback callback) throws XMPPException {
		return Observable.create(new Observable.OnSubscribe<Void>() {
			@Override
			public void call(Subscriber<? super Void> subscriber) {
				try {
					loginAccount();
					mCallback = callback;
					mMultiUserChat = new MultiUserChat(mXMPPConnection, room);
					mMultiUserChat.addMessageListener(mPacketListener);
					mCurrentRoom = room;
					mMultiUserChat.join(mUsername);
					subscriber.onCompleted();
				} catch (Exception e) {
					subscriber.onError(e);
				}
			}
		});
	}

	public Observable<Void> sendDanma(final String message) {
		return Observable.create(new Observable.OnSubscribe<Void>() {
			@Override
			public void call(Subscriber<? super Void> subscriber) {
				try {
					login();
					mMultiUserChat.sendMessage(message);
					subscriber.onCompleted();
				} catch (Exception e) {
					subscriber.onError(e);
				}
			}
		});
	}

	public Observable<Void> leaveRoom() {
		return Observable.create(new Observable.OnSubscribe<Void>() {
			@Override
			public void call(Subscriber<? super Void> subscriber) {

				try {
					if (mMultiUserChat != null && mMultiUserChat.isJoined()) {
						mMultiUserChat.leave();
						mMultiUserChat.removeMessageListener(mPacketListener);
					}
					mMultiUserChat = null;
					subscriber.onCompleted();
				} catch (Exception e) {
					subscriber.onError(e);
				}
			}
		});
	}

	public void setCallback(Callback callback) {
		mCallback = callback;
	}

	public interface Callback {
		void onMessageReceived(boolean isToMe, String message);
	}
}
