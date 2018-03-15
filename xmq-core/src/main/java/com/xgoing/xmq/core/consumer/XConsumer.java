package com.xgoing.xmq.core.consumer;

import com.xgoing.xmq.core.destination.XDestination;
import com.xgoing.xmq.core.listener.XListener;
import com.xgoing.xmq.core.message.XMessage;

/**
 * 消费者.
 */
public interface XConsumer<H> {
    /**
     * 同步接收消息.
     *
     * @param destination
     * @return
     */
    XMessage recive(XDestination destination);

    /**
     * 设置消息监听器.
     *
     * @param handler
     */
    void setListener(XListener<H> handler);
}
