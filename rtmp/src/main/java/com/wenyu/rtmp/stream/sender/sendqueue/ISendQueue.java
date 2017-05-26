package com.wenyu.rtmp.stream.sender.sendqueue;

import com.wenyu.rtmp.entity.Frame;

public interface ISendQueue {
    void start();
    void stop();
    void setBufferSize(int size);
    void putFrame(Frame frame);
    Frame takeFrame();
    void setSendQueueListener(SendQueueListener listener);
}
