package com.xgoing.xmq.core.message;

/**
 * 消息抽象.
 */
public interface XMessage<T> {
    /**
     * 消息内容.
     */
    T getMessage();
}
