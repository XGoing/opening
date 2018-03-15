package com.xgoing.xmq.core.url.handler;

public interface XConnectUrlResolver {
    /**
     * 协议.
     * @return
     */
    String getProtocol();
    /**
     * MQ模型(ps,pp).
     * @return
     */
    String getMQMode();
    /**
     * url(host:port).
     * @return
     */
    String getUrl();
    /**
     * 主机名.
     * @return
     */
    String getHost();
    /**
     * 端口.
     * @return
     */
    int getPort();
    /**
     * 用户名.
     * @return
     */
    String getUserName();
    /**
     * 密码.
     * @return
     */
    String getPassword();
}
