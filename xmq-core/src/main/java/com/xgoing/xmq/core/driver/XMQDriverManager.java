package com.xgoing.xmq.core.driver;

import com.xgoing.xmq.core.configuration.XConfiguration;
import com.xgoing.xmq.core.connection.XConnection;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * MQ驱动管理器.
 */
public class XMQDriverManager {
    /**
     * 已经注册的MQ驱动列表.
     */
    private static final List<XMQDriver> registeredDrivers = new CopyOnWriteArrayList<XMQDriver>();

    public static final void register(XMQDriver driver) {
        if (!registeredDrivers.contains(driver)) {
            registeredDrivers.add(driver);
        }
    }

    /**
     * 获取JMS客户端连接.
     *
     * @param config
     * @return
     */
    public static final XConnection getConection(XConfiguration config) {
        String connectUrl = config.getConnectUrl();
        String protocolType = connectUrl.split(":")[1];
        XMQDriver driver = findDriverRegisted(protocolType);
        if (driver != null) {
            return driver.connect(config);
        }

        return null;
    }

    /**
     * 按照连接协议验证是否有对应的驱动已经注册,并返回注册的驱动对象.
     *
     * @param protocol
     * @return
     */
    private static XMQDriver findDriverRegisted(final String protocol) {
        return registeredDrivers.parallelStream().filter(xmqDriver -> xmqDriver.getType().toString().equals(protocol)).findAny().get();
    }
}
