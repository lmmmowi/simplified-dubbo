package com.lmmmowi.simplified.dubbo.config;

import com.lmmmowi.simplified.dubbo.common.URL;
import com.lmmmowi.simplified.dubbo.common.constants.ConfigConstants;
import com.lmmmowi.simplified.dubbo.extension.ExtensionLoader;
import com.lmmmowi.simplified.dubbo.rpc.Exporter;
import com.lmmmowi.simplified.dubbo.rpc.Invoker;
import com.lmmmowi.simplified.dubbo.rpc.Protocol;
import com.lmmmowi.simplified.dubbo.rpc.ProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @Author: mowi
 * @Date: 2019-05-30
 * @Description:
 */
public class ServiceConfig<T> extends AbstractConfig {

    private static final Logger logger = LoggerFactory.getLogger(ServiceConfig.class);

    private static final Protocol PROTOCOL = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();

    private static final ProxyFactory PROXY_FACTORY = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();

    private final List<Exporter<?>> exporters = new ArrayList<Exporter<?>>();

    protected T ref;

    protected Class<?> interfaceClass;

    protected String interfaceName;

    protected String path;

    protected ProviderConfig provider;

    protected List<ProtocolConfig> protocols;

    public void setRef(T ref) {
        this.ref = ref;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public void setPath(String path) {
        this.path = path;
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
        if (StringUtils.isEmpty(path)) {
            path = interfaceName;
        }

        doExportUrls();
    }

    private void doExportUrls() {
        List<URL> registryURLs = loadRegistries();

        for (ProtocolConfig protocol : protocols) {
            doExportUrlsFor1Protocol(protocol, registryURLs);
        }
    }

    private List<URL> loadRegistries() {
        return Collections.EMPTY_LIST;
    }

    private void doExportUrlsFor1Protocol(ProtocolConfig protocolConfig, List<URL> registryURLs) {
        Map<String, String> parameters = new HashMap<String, String>();
        String name = protocolConfig.getName();
        String host = this.findConfigedHosts(protocolConfig, registryURLs, null);
        Integer port = this.findConfigedPorts(protocolConfig, null);
        String urlPath = this.getContextPath(protocolConfig).orElse(path);

        URL url = new URL(name, host, port, urlPath, parameters);

        logger.info("export url for service [" + interfaceClass.getName() + "], PROTOCOL [" + protocolConfig.getName() + "], url is " + url.toString());

        String scope = url.getParameter(ConfigConstants.SCOPE_KEY);
        if (!ConfigConstants.SCOPE_NONE.equalsIgnoreCase(scope)) {
            if (!ConfigConstants.SCOPE_LOCAL.equalsIgnoreCase(scope)) {
                exportLocal(url);
            }
            if (!ConfigConstants.SCOPE_REMOTE.equalsIgnoreCase(scope)) {
//                exportRemote(url);
            }
        }
    }

    private void exportLocal(URL url) {
        Invoker<?> invoker = PROXY_FACTORY.getInvoker(ref, (Class) interfaceClass, url);
        Exporter<?> exporter = PROTOCOL.export(invoker);
        exporters.add(exporter);
        logger.info("Export dubbo service " + interfaceClass.getName() + " to local registry url : " + url);
    }

    private String findConfigedHosts(ProtocolConfig protocolConfig, List<URL> registryURLs, Map<String, String> map) {
        String hostToBind = protocolConfig.getHost();
        String hostToRegistry = hostToBind;
        return hostToRegistry;
    }

    private Integer findConfigedPorts(ProtocolConfig protocolConfig, Map<String, String> map) {
        Integer portToBind = protocolConfig.getPort();
        Integer portToRegistry = portToBind;
        return portToRegistry;
    }

    private Optional<String> getContextPath(ProtocolConfig protocolConfig) {
        return Optional.ofNullable(null);
    }
}
