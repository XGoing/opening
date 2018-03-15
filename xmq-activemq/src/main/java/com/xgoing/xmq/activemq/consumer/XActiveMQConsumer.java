package com.xgoing.xmq.activemq.consumer;

import com.xgoing.xmq.core.consumer.XConsumer;
import com.xgoing.xmq.core.destination.XDestination;
import com.xgoing.xmq.core.listener.XListener;
import com.xgoing.xmq.core.message.XMessage;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;

public class XActiveMQConsumer implements XConsumer<MessageListener> {
    private MessageConsumer consumer;
    private String brokerURL;

    public XActiveMQConsumer(MessageConsumer consumer) {
        this.consumer = consumer;
    }

    public XActiveMQConsumer(MessageConsumer consumer, String brokerURL) {
        this.consumer = consumer;
        this.brokerURL = brokerURL;
    }

    @Override
    public XMessage recive(XDestination destination) {
        return null;
    }

    @Override
    public void setListener(XListener<MessageListener> listener) {
        try {
            consumer.setMessageListener(listener.getHandler());
        } catch (JMSException jmse) {
            jmse.printStackTrace();
        }
    }
}
