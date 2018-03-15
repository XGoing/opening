package com.xgoing.xmq.activemq.connection;


import com.xgoing.xmq.core.connection.XConnection;

/**
 * 连接工厂.
 */
public interface ConnectionFactory {
    /**
     * 创建连接.
     *
     * @return
     */
    XConnection createConnection();
}
