package com.xgoing.xmq.activemq.driver;

import com.xgoing.xmq.core.driver.XMQDriverManager;

public class XActiveMQDriver extends XActiveMQDriverBase {
    /**
     * 注册驱动.
     */
    static {
        XMQDriverManager.register(new XActiveMQDriver());
    }

}
