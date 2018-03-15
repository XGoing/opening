package com.xgoing.xmq.core.driver;

import com.xgoing.xmq.core.configuration.XConfiguration;
import com.xgoing.xmq.core.connection.XConnection;

public interface XMQDriver {
    MQDriverType getType();

    /**
     * 初始化MQ类型.
     */
    void initType(MQDriverType type);

    /**
     * 获取连接
     *
     * @param config
     * @return
     */
    XConnection connect(XConfiguration config);

    enum MQDriverType {
        activeMQ, rocketMQ, kafka
    }
}
