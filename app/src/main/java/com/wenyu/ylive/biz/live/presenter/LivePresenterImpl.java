package com.wenyu.ylive.biz.live.presenter;

import com.google.gson.JsonElement;
import com.wenyu.mvp.presenter.BaseMvpPresenter;
import com.wenyu.ylive.biz.live.model.ILiveModel;
import com.wenyu.ylive.biz.live.view.ILiveView;
import com.wenyu.ylive.common.bean.Broadcast;
import com.wenyu.ylive.common.rx.YLiveSubscriber;

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
                                ILiveView.Data viewData = new ILiveView.Data();
                                viewData.chatUrl = data.chatUrl;
                                viewData.liveUrl = data.liveUrl;
                                mView.render(viewData);
                            }
                        }));
            }
        });
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
