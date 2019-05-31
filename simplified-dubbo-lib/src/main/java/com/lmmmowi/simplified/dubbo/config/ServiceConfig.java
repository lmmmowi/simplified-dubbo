package com.lmmmowi.simplified.dubbo.config;

import com.lmmmowi.simplified.dubbo.common.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @Author: mowi
 * @Date: 2019-05-30
 * @Description:
 */
public class ServiceConfig<T> extends AbstractConfig {

    private static final Logger logger = LoggerFactory.getLogger(ServiceConfig.class);

    protected T ref;

    protected Class<?> interfaceClass;

    protected ProviderConfig provider;

    protected List<ProtocolConfig> protocols;

    public void setRef(T ref) {
        this.ref = ref;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setProvider(ProviderConfig provider) {
        this.provider = provider;
    }

    public void setProtocols(List<ProtocolConfig> protocols) {
        this.protocols = protocols;
    }

    public synchronized void export() {
        checkAndUpdateSubConfigs();

        doExport();
    }

    private void checkAndUpdateSubConfigs() {
        createProviderIfAbsent();
        checkProtocol();
    }

    private void createProviderIfAbsent() {
        if (provider != null) {
            return;
        }

        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.refresh();
        setProvider(providerConfig);
    }

    private void checkProtocol() {
        if (CollectionUtils.isEmpty(protocols) && provider != null) {
            setProtocols(provider.getProtocols());
        }
    }

    private void doExport() {
        doExportUrls();
    }

    private void doExportUrls() {
        List<URL> registryURLs = loadRegistries();

        for (ProtocolConfig protocol : protocols) {
            doExportUrlsFor1Protocol(protocol, registryURLs);
        }
    }

    private void doExportUrlsFor1Protocol(ProtocolConfig protocol, List<URL> registryURLs) {
        logger.info("export url for service [" + interfaceClass.getName() + "], protocol [" + protocol.getName() + "]");
    }

    private List<URL> loadRegistries() {
        return Collections.EMPTY_LIST;
    }
}
