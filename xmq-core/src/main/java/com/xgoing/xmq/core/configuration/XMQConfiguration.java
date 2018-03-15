package com.xgoing.xmq.core.configuration;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;

/**
 * MQ配置.
 */
public class XMQConfiguration implements XConfiguration {
    /**
     * 连接信息.
     */
    private String connectUrl;
    /**
     * 消息持久化.
     */
    private boolean persistence;
    /**
     * 消息过期时间.
     */
    private Long expireTime;
    /**
     * 消息确认策略.
     */
    private int confirmPolicy;
    /**
     * 持久订阅.
     */
    private boolean durableSubscription;

    private volatile static XMQConfiguration config;


    private XMQConfiguration() {
    }

    private void setConnectUrl(String connectUrl) {
        this.connectUrl = connectUrl;
    }

    @Override
    public String getConnectUrl() {
        return this.connectUrl;
    }

    @Override
    public boolean isPersistence() {
        return persistence;
    }

    private void setPersistence(boolean persistence) {
        this.persistence = persistence;
    }

    @Override
    public Long getExpireTime() {
        return expireTime;
    }

    private void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public int getConfirmPolicy() {
        return confirmPolicy;
    }

    private void setConfirmPolicy(int confirmPolicy) {
        this.confirmPolicy = confirmPolicy;
    }

    @Override
    public boolean isDurableSubscription() {
        return durableSubscription;
    }

    private void setDurableSubscription(boolean durableSubscription) {
        this.durableSubscription = durableSubscription;
    }

    public static final class XmlConfigFactory {
        private static final Object lock = new Object();
        /**
         * 配置文件位置.
         */
        private static String configLocation;

        public static final XConfiguration newConfiguration(String connectUrl, boolean persistence, Long expireTime, int confirmPolicy, boolean durableSubscription) {
            if (config == null) {
                synchronized (lock) {
                    config = new XMQConfiguration();
                    config.setConnectUrl(connectUrl);
                    config.setPersistence(persistence);
                    config.setExpireTime(expireTime);
                    config.setConfirmPolicy(confirmPolicy);
                    config.setDurableSubscription(durableSubscription);
                }
            }
            return config;
        }

        public static final XConfiguration newConfiguration(String configLocation) {
            if (config == null) {
                //保证加载解析配置与构造配置对象的事务性
                synchronized (lock) {
                    if (configLocation != null && configLocation.trim().length() > 0) {
                        config = new XMQConfiguration();
                        InputStream configIns = null;
                        try {
                            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                            configIns = Thread.currentThread().getContextClassLoader().getResourceAsStream(configLocation);
                            Document doc = docBuilder.parse(configIns);
                            NodeList nodeList = doc.getElementsByTagName("mq");
                            if (nodeList.getLength() == 1) {
                                Node mq = nodeList.item(0);
                                NodeList childNodes = mq.getChildNodes();
                                if (childNodes.getLength() > 0) {
                                    for (int i = 0; i < childNodes.getLength(); i++) {
                                        Node node = childNodes.item(i);
                                        String value = node.getTextContent();
                                        String nodeName = node.getNodeName();
                                        if ("connect-url".equals(nodeName)) {
                                            config.setConnectUrl(value.trim());
                                        }

                                        if ("confirm-policy".equals(nodeName)) {
                                            int _policy = 0;
                                            try {
                                                if (value != null) {
                                                    value = value.trim();
                                                }
                                                _policy = Integer.valueOf(value);
                                            } catch (NumberFormatException nfe) {
                                                nfe.printStackTrace();
                                            }
                                            config.setConfirmPolicy(_policy);
                                        }

                                        if ("persistence".equals(nodeName) || "durable-subscription".equals(nodeName)) {
                                            boolean _value = false;
                                            try {
                                                if (value != null) {
                                                    value = value.trim();
                                                }
                                                _value = Boolean.valueOf(value);
                                            } catch (NumberFormatException nfe) {
                                                nfe.printStackTrace();
                                            }
                                            if ("persistence".equals(nodeName)) {
                                                config.setPersistence(_value);
                                            } else {
                                                config.setDurableSubscription(_value);
                                            }
                                        }

                                        if ("expire-time".equals(nodeName)) {
                                            Long _expireTime = null;
                                            try {
                                                if (value != null) {
                                                    value = value.trim();
                                                }
                                                _expireTime = Long.valueOf(value);
                                            } catch (NumberFormatException nfe) {
                                                nfe.printStackTrace();
                                            }
                                            config.setExpireTime(_expireTime);
                                        }
                                    }
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (configIns != null) {
                                try {
                                    configIns.close();
                                } catch (IOException ioe) {
                                    ioe.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
            return config;
        }
    }
}
