package com.xgoing.xmq.activemq.listener;

import com.xgoing.xmq.core.listener.XListener;

import javax.jms.MessageListener;

public class XActiveMQConsumerListenerAdapter implements XListener<MessageListener> {
    private MessageListener listener;

    public XActiveMQConsumerListenerAdapter(MessageListener listener) {
        this.listener = listener;
    }

    @Override
    public MessageListener getHandler() {
        return this.listener;
    }
}
