package com.xgoing.xmq.activemq.driver;

import com.xgoing.xmq.activemq.connection.ConnectionFactory;
import com.xgoing.xmq.activemq.connection.XActiveMQConnectionFactory;
import com.xgoing.xmq.core.configuration.XConfiguration;
import com.xgoing.xmq.core.connection.XConnection;
import com.xgoing.xmq.core.driver.XMQDriver;

public abstract class XActiveMQDriverBase implements XMQDriver {
    /**
     * 类型.
     */
    private volatile MQDriverType type;

    public XActiveMQDriverBase() {
        this.initType(MQDriverType.activeMQ);
    }

    @Override
    public MQDriverType getType() {
        return this.type;
    }

    @Override
    public void initType(MQDriverType type) {
        if (this.type == null) {
            this.type = type;
        }
    }

    @Override
    public XConnection connect(XConfiguration config) {
        ConnectionFactory connectionFactory = XActiveMQConnectionFactory.getInstance(config);
        return connectionFactory.createConnection();
    }
}
