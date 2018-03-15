package com.xgoing.xmq.activemq.connection;

import com.xgoing.xmq.core.configuration.XConfiguration;
import com.xgoing.xmq.core.connection.XConnection;
import com.xgoing.xmq.core.exception.XMQException;
import com.xgoing.xmq.core.url.handler.XConnectUrlResolver;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.transport.TransportListener;

import javax.jms.Connection;
import javax.jms.JMSException;
import java.io.IOException;

public class XActiveMQConnectionFactory implements ConnectionFactory {
    private XConfiguration config;
    private ActiveMQConnectionFactory activeMQConnectionFactory;
    private Connection connection;
    private String brokerURL;
    private String userName;
    private String password;
    private volatile static XActiveMQConnectionFactory factory;
    private static final Object lock = new Object();

    private XActiveMQConnectionFactory(XConfiguration config) {
        this.config = config;
        XConnectUrlResolver resolver = new XActiveMqConnectUrlResolver(config.getConnectUrl());
        this.brokerURL = resolver.getUrl();
        this.userName = ActiveMQConnectionFactory.DEFAULT_USER;
        if (resolver.getUserName() != null) {
            this.userName = resolver.getUserName();
        }
        this.password = ActiveMQConnectionFactory.DEFAULT_PASSWORD;

        if (resolver.getPassword() != null) {
            this.password = resolver.getPassword();
        }
        int constFlag = 0;
        if (assertNotNull(brokerURL)) {
            brokerURL = trim(brokerURL);
            constFlag = 1;
        }
        if (assertNotNull(userName) && assertNotNull(password)) {
            userName = trim(userName);
            password = trim(password);
            constFlag = 2;
        }

        switch (constFlag) {
            case 1:
                this.activeMQConnectionFactory = new ActiveMQConnectionFactory(brokerURL);
                break;
            case 2:
                this.activeMQConnectionFactory = new ActiveMQConnectionFactory(userName, password, brokerURL);
                break;
            default:
                this.activeMQConnectionFactory = new ActiveMQConnectionFactory();
                break;
        }
        //添加断开监听器
        this.activeMQConnectionFactory.setTransportListener(new TransportListener() {
            @Override
            public void onCommand(Object o) {
                System.out.println("o = [" + o + "]");
            }

            @Override
            public void onException(IOException e) {
                System.out.println("e = [" + e + "]");
            }

            @Override
            public void transportInterupted() {
                System.out.println("断开" + System.currentTimeMillis());
            }

            @Override
            public void transportResumed() {
                System.out.println("重连" + System.currentTimeMillis());
            }
        });

        this.connectMq();
    }

    private void connectMq() {
        try {
            System.out.println("连接ActiveMQ.");
            this.connection = this.activeMQConnectionFactory.createConnection();
            this.connection.start();
        } catch (JMSException jmse) {
            jmse.printStackTrace();
        }
    }

    private boolean assertNotNull(String param) {
        return param != null && param.trim().length() > 0;
    }

    private String trim(String param) {
        return param.trim();
    }

    @Override
    public XConnection createConnection() {
        return new XActiveMQConnection(this.connection, this.config);
    }

    public static final XActiveMQConnectionFactory getInstance(XConfiguration config) {
        if (factory == null) {
            synchronized (lock) {
                factory = new XActiveMQConnectionFactory(config);
            }
        }

        return factory;
    }

    private class XActiveMqConnectUrlResolver implements XConnectUrlResolver {
        private String connectUrl;
        private String protocol;
        private String MQMode;
        private String url;

        private XActiveMqConnectUrlResolver(String connectUrl) {
            if (!connectUrl.startsWith("xmq")) {
                throw new XMQException("解析连接错误，不是xmq协议.");
            }

            this.connectUrl = connectUrl;
            String[] arr = this.connectUrl.split(":");
            this.protocol = arr[1];
            this.MQMode = arr[2];
            int headLen = (arr[0] + this.protocol + this.MQMode).length() + 3;
            String url = connectUrl.substring(headLen);
            this.url = url;
        }

        @Override
        public String getProtocol() {
            return this.protocol;
        }

        @Override
        public String getMQMode() {
            return this.MQMode;
        }

        @Override
        public String getUrl() {
            return this.url;
        }

        @Override
        public String getHost() {
            return "";
        }

        @Override
        public int getPort() {
            return -1;
        }

        @Override
        public String getUserName() {
            return ActiveMQConnectionFactory.DEFAULT_USER;
        }

        @Override
        public String getPassword() {
            return ActiveMQConnectionFactory.DEFAULT_PASSWORD;
        }
    }
}
