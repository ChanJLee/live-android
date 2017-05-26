package com.wenyu.rtmp.stream.sender.rtmp.io;

import com.wenyu.rtmp.stream.sender.rtmp.packets.Chunk;

public interface OnReadListener {
    void onChunkRead(Chunk chunk);
    void onDisconnect();
    void onStreamEnd();
}
