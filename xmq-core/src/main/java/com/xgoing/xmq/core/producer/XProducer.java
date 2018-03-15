package com.xgoing.xmq.core.producer;

import com.xgoing.xmq.core.message.XMessage;

import javax.security.auth.callback.Callback;

/**
 * 生产者.
 */
public interface XProducer {
    int send(XMessage message);

    void send(XMessage message, Runnable task);

    void send(XMessage message, Callback callback);
}
