package com.xgoing.xmq.activemq.producer;

import com.xgoing.xmq.core.exception.XMQException;
import com.xgoing.xmq.core.message.XMessage;
import com.xgoing.xmq.core.producer.XProducer;

import javax.jms.*;
import javax.security.auth.callback.Callback;

public class XActiveMQProducer implements XProducer {
    private MessageProducer producer;
    private Session session;

    public XActiveMQProducer(MessageProducer producer, Session session) {
        this.producer = producer;
        this.session = session;
    }

    @Override
    public int send(XMessage message) {
        try {
            Object msg = message.getMessage();
            if (msg instanceof Message || msg instanceof TextMessage) {
                this.producer.send((Message) msg);
            } else {
                throw new XMQException("not support message type!");
            }
            return 1;
        } catch (JMSException jmse) {
            jmse.printStackTrace();
        }
        return 0;
    }

    @Override
    public void send(XMessage message, Runnable task) {
    }

    @Override
    public void send(XMessage message, Callback callback) {
    }
}
