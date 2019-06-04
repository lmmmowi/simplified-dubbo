package com.lmmmowi.simplified.dubbo.config;

import com.lmmmowi.simplified.dubbo.rpc.Constants;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: mowi
 * @Date: 2019-05-31
 * @Description:
 */
public class ProviderConfig extends AbstractConfig {

    protected List<ProtocolConfig> protocols;

    public List<ProtocolConfig> getProtocols() {
        return protocols;
    }

    public void setProtocols(List<ProtocolConfig> protocols) {
        this.protocols = protocols;
    }

    @Override
    public void refresh() {
        if (CollectionUtils.isEmpty(protocols)) {
            protocols = new ArrayList<>();
            ProtocolConfig protocolConfig = new ProtocolConfig();
            protocolConfig.setName(Constants.LOCAL_PROTOCOL);
            protocolConfig.setHost("localhost");
            protocolConfig.setPort(1);
            protocols.add(protocolConfig);
        }

        super.refresh();
    }
}
