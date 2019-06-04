package com.lmmmowi.simplified.dubbo.common;

import com.lmmmowi.simplified.dubbo.common.constants.CommonConstants;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @Author: mowi
 * @Date: 2019-05-31
 * @Description:
 */
public class URL {

    private String protocol;

    private String host;

    private int port;

    private String path;

    private Map<String, String> parameters;

    public URL(String protocol, String host, int port, String path, Map<String, String> parameters) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.path = path;
        this.parameters = parameters;
    }

    public String getParameter(String key) {
        String value = parameters.get(key);
        if (StringUtils.isEmpty(value)) {
            value = parameters.get(CommonConstants.DEFAULT_KEY_PREFIX + key);
        }
        return value;
    }

    @Override
    public String toString() {
        return buildString(true);
    }

    private String buildString(boolean appendParameter) {
        StringBuilder buf = new StringBuilder();
        if (!StringUtils.isEmpty(protocol)) {
            buf.append(protocol);
            buf.append("://");
        }
        if (host != null && host.length() > 0) {
            buf.append(host);
            if (port > 0) {
                buf.append(":");
                buf.append(port);
            }
        }
        if (path != null && path.length() > 0) {
            buf.append("/");
            buf.append(path);
        }
        if (appendParameter) {
            buildParameters(buf, true, null);
        }
        return buf.toString();
    }

    private void buildParameters(StringBuilder buf, boolean concat, String[] parameters) {

    }
}
