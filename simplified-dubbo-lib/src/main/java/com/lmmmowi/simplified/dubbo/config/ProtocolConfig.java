package com.lmmmowi.simplified.dubbo.config;

/**
 * @Author: mowi
 * @Date: 2019-05-31
 * @Description:
 */
public class ProtocolConfig extends AbstractConfig {

    private String name;

    private String host;

    private Integer port;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
