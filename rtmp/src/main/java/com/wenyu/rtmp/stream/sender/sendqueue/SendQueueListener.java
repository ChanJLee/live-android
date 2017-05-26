package com.wenyu.rtmp.stream.sender.sendqueue;

public interface SendQueueListener {
    void good();
    void bad();
}
