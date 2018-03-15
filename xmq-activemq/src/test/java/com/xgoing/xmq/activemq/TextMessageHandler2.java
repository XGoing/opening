package com.xgoing.xmq.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class TextMessageHandler2 implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("监听到消息...");
            //消息监听器
            System.out.println("2 >>>>>>" + ((TextMessage) message).getText());
        } catch (JMSException jmse) {
            jmse.printStackTrace();
        }
    }
}
