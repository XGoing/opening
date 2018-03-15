package com.xgoing.xmq.activemq;

import com.xgoing.xmq.activemq.driver.XActiveMQDriver;
import com.xgoing.xmq.activemq.listener.XActiveMQConsumerListenerAdapter;
import com.xgoing.xmq.activemq.message.TextMessage;
import com.xgoing.xmq.activemq.message.XBytesMessage;
import com.xgoing.xmq.core.configuration.XConfiguration;
import com.xgoing.xmq.core.configuration.XMQConfiguration;
import com.xgoing.xmq.core.connection.XConnection;
import com.xgoing.xmq.core.driver.XMQDriverManager;
import com.xgoing.xmq.core.listener.XListener;
import com.xgoing.xmq.core.message.XMessage;
import com.xgoing.xmq.core.producer.XProducer;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.MessageListener;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        try {
            Class.forName(XActiveMQDriver.class.getName());
//            XConfiguration config = XMQConfiguration.XmlConfigFactory.newConfiguration("xmq-config.xml");
            XConfiguration config = XMQConfiguration.XmlConfigFactory.newConfiguration("xmq:activeMQ:ps:failover:(tcp://localhost:61616)?initialReconnectDelay=100", true, null, 0, false);
            XConnection connection = XMQDriverManager.getConection(config);
            System.out.println(config.getConnectUrl());
            String topic = "hello@activeMQ";
            XListener<MessageListener> handler = new XActiveMQConsumerListenerAdapter(new TextMessageHandler());
            connection.createConsumer(topic, handler);
            XListener<MessageListener> handler2 = new XActiveMQConsumerListenerAdapter(new TextMessageHandler2());
            connection.createConsumer(topic, handler2);
            XProducer producer = connection.createProducer(topic);
            while (true) {
                //发送消息
                javax.jms.TextMessage msg = new ActiveMQTextMessage();
                msg.setText("hello mq!");
                XMessage<javax.jms.TextMessage> message = new TextMessage(msg);
                producer.send(message);
                TimeUnit.SECONDS.sleep(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
