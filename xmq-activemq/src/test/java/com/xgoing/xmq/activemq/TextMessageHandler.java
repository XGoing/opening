package com.xgoing.xmq.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class TextMessageHandler implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            //消息监听器
            System.out.println("1 >>>>>>" + ((TextMessage) message).getText());
        } catch (JMSException jmse) {
            jmse.printStackTrace();
        }
    }
}
