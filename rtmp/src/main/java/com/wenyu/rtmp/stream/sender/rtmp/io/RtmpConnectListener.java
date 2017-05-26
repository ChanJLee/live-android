package com.wenyu.rtmp.stream.sender.rtmp.io;

public interface RtmpConnectListener {
    void onUrlInvalid();
    void onSocketConnectSuccess();
    void onSocketConnectFail();
    void onHandshakeSuccess();
    void onHandshakeFail();
    void onRtmpConnectSuccess();
    void onRtmpConnectFail();
    void onCreateStreamSuccess();
    void onCreateStreamFail();
    void onPublishSuccess();
    void onPublishFail();
    void onSocketDisconnect();
    void onStreamEnd();
}
