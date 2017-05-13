package com.wenyu.ylive.biz.live.presenter;

import android.util.Log;

import com.google.gson.JsonElement;
import com.wenyu.mvp.presenter.BaseMvpPresenter;
import com.wenyu.xmpp.XmppClient;
import com.wenyu.ylive.biz.live.model.ILiveModel;
import com.wenyu.ylive.biz.live.view.ILiveView;
import com.wenyu.ylive.common.bean.Broadcast;
import com.wenyu.ylive.common.rx.YLiveSubscriber;

import org.jivesoftware.smack.XMPPException;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chan on 17/5/2.
 */

public class LivePresenterImpl extends BaseMvpPresenter<ILiveView, ILiveModel> implements ILivePresenter {

    public LivePresenterImpl(ILiveView view, ILiveModel model) {
        super(view, model);
    }

    @Override
    protected void onAttach() {
        mView.setEventListener(new LiveEventListener() {
            @Override
            public void onLiveChecked(boolean checked) {
                if (checked) {
                    handleOpenBroadcast();
                } else {
                    handleCloseBroadcast();
                }
            }

            @Override
            public void onRoomConfigFinished() {
                add(getModel().openBroadcast(mView.fetchTitle(), mView.fetchCategory())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new YLiveSubscriber<Broadcast>() {
                            @Override
                            public void onResponseFailed(Throwable e) {
                                mView.showToast("网络错误");
                            }

                            @Override
                            public void onResponseSuccess(Broadcast data) {
                                mView.render(data.liveUrl);
                                setupChatRoom(data.chatUrl);
                            }
                        }));
            }
        });
    }

    private void setupChatRoom(String room) {
        try {
            getModel().getXmppClient().enterRoom(room,
                    new XmppClient.Callback() {
                        @Override
                        public void onMessageReceived(boolean isToMe, String message) {
                            if (mView != null) {
                                mView.renderDanma(message);
                            }
                        }
                    }).subscribeOn(Schedulers.io())
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
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }

    private void handleOpenBroadcast() {
        mView.showRoomConfigDialog();
    }

    private void handleCloseBroadcast() {
        add(mModel.closeBroadcast()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new YLiveSubscriber<JsonElement>() {
                    @Override
                    public void onResponseFailed(Throwable e) {
                        mView.showToast("网络错误关播失败");
                    }

                    @Override
                    public void onResponseSuccess(JsonElement data) {
                        mView.showToast("关播成功");
                    }
                }));
    }

    @Override
    protected void onDetach() {
        mView.release();
        mView.setEventListener(null);
    }

    @Override
    public void onActivityStop() {
        mView.pause();
    }

    @Override
    public void onActivityStart() {
        mView.resume();
    }
}
