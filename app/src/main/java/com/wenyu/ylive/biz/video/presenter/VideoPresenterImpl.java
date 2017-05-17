package com.wenyu.ylive.biz.video.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.wenyu.mvp.presenter.BaseMvpPresenter;
import com.wenyu.xmpp.XmppClient;
import com.wenyu.ylive.biz.video.model.IVideoModel;
import com.wenyu.ylive.biz.video.video.IVideoView;
import com.wenyu.ylive.common.bean.Room;

import org.jivesoftware.smack.XMPPException;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chan on 17/4/24.
 */

public class VideoPresenterImpl extends BaseMvpPresenter<IVideoView, IVideoModel> implements IVideoPresenter {

    public VideoPresenterImpl(IVideoView view, IVideoModel model) {
        super(view, model);
    }

    @Override
    protected void onAttach() {
        mView.setEventListener(new VideoEventListener() {

            @Override
            public void onSendClicked() {
                String input = mView.getInput();
                if (TextUtils.isEmpty(input)) {
                    return;
                }
                mModel.fetchXmppClient().sendDanma(input)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Void>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Void aVoid) {

                            }
                        });
                mView.clearInput();
            }
        });
    }

    @Override
    protected void onDetach() {
        mView = null;
    }

    @Override
    public void init(Room room) {
        IVideoView.Data data = new IVideoView.Data();
        data.title = room.title;
        data.anchor = room.anchor;
        data.cover = room.snapshot;
        data.url = room.liveUrl;
        setupChatRoom(room.chatRoom);
        mView.render(data);
    }

    private void setupChatRoom(String chatRoom) {
        if (TextUtils.isEmpty(chatRoom)) {
            return;
        }

        try {
            getModel().fetchXmppClient().enterRoom(chatRoom,
                    new XmppClient.Callback() {
                        @Override
                        public void onMessageReceived(boolean isToMe, String message) {
                            if (mView != null) {
                                mView.renderDanma(message);
                            }
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Void>() {
                        @Override
                        public void onCompleted() {
                            Log.d("chan_debug", "complete");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("chan_debug", "error");
                        }

                        @Override
                        public void onNext(Void aVoid) {
                            Log.d("chan_debug", "next");
                        }
                    });
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }
}
