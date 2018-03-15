package com.xgoing.xmq.activemq.message;

import com.xgoing.xmq.core.message.XMessage;

import javax.jms.BytesMessage;

public class XBytesMessage implements XMessage<BytesMessage> {
    private BytesMessage message;

    public XBytesMessage(BytesMessage message) {
        this.message = message;
    }

    @Override
    public BytesMessage getMessage() {
        return this.message;
    }
}
