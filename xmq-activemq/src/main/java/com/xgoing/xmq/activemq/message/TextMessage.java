package com.xgoing.xmq.activemq.message;

import com.xgoing.xmq.core.message.XMessage;

public class TextMessage implements XMessage<javax.jms.TextMessage> {
    private javax.jms.TextMessage message;

    public TextMessage(javax.jms.TextMessage message) {
        this.message = message;
    }

    @Override
    public javax.jms.TextMessage getMessage() {
        return this.message;
    }
}
