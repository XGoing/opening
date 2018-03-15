package com.xgoing.xmq.core.configuration;

/**
 * MQ配置信息.
 */
public interface XConfiguration {
    /**
     * 获取连接信息.
     *
     * @return
     */
    String getConnectUrl();

    /**
     * 持久化消息.
     *
     * @return
     */
    boolean isPersistence();

    /**
     * 消息过期时长.
     *
     * @return
     */
    Long getExpireTime();

    /**
     * 消息确认策略.
     *
     * @return
     */
    int getConfirmPolicy();

    /**
     * 持久订阅.
     *
     * @return
     */
    boolean isDurableSubscription();
}
