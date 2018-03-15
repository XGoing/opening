package com.xgoing.xmq.activemq.connection;

import com.xgoing.xmq.activemq.consumer.XActiveMQConsumer;
import com.xgoing.xmq.activemq.producer.XActiveMQProducer;
import com.xgoing.xmq.core.configuration.XConfiguration;
import com.xgoing.xmq.core.connection.XConnection;
import com.xgoing.xmq.core.consumer.XConsumer;
import com.xgoing.xmq.core.listener.XListener;
import com.xgoing.xmq.core.producer.XProducer;

import javax.jms.*;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class XActiveMQConnection implements XConnection {
    private final Map<String, XProducer> producerMap = new ConcurrentHashMap<>();
    private Connection connection;
    private XConfiguration config;

    public XActiveMQConnection(Connection connection, XConfiguration config) {
        this.connection = connection;
        this.config = config;
    }

    @Override
    public XProducer createProducer(String topic) {
        try {
            //生产者缓存key
            String producerKey = topic;
            XProducer xProducer = producerMap.get(producerKey);
            if (xProducer == null) {
                //建立会话,第一个参数代表是否开启事务，第二个参数代表消息确认机制
                Session session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Destination destination = session.createTopic(topic);
                MessageProducer producer = session.createProducer(destination);
                //消息持久化设置
                if (config.isPersistence()) {
                    producer.setDeliveryMode(DeliveryMode.PERSISTENT);
                } else {
                    producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                }

                xProducer = new XActiveMQProducer(producer, session);
                producerMap.put(producerKey, xProducer);
            }
            return xProducer;
        } catch (JMSException jmse) {
            jmse.printStackTrace();
        }
        return null;
    }

    @Override
    public XConsumer createConsumer(String topic, XListener listener) {
        try {
            //开启事务保证数据安全
            Session session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic(topic);
            MessageConsumer consumer = session.createConsumer(destination);
            XConsumer xConsumer = new XActiveMQConsumer(consumer);
            xConsumer.setListener(listener);
            return xConsumer;
        } catch (JMSException jmse) {
            jmse.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() throws IOException {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (JMSException jmse) {
                jmse.printStackTrace();
            }
        }
    }
}
