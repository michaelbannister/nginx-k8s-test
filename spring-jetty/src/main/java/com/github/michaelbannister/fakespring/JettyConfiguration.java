package com.github.michaelbannister.fakespring;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JettyConfiguration {

    @Bean
    public EmbeddedServletContainerFactory embeddedServletContainerFactory(
            @Value("${jetty.threadPool.maxThreads:200}") final String maxThreads,
            @Value("${jetty.threadPool.minThreads:10}") final String minThreads,
            @Value("${jetty.threadPool.idleTimeout:6000}") final String idleTimeout
    ) {
        final JettyEmbeddedServletContainerFactory factory = new JettyEmbeddedServletContainerFactory() {
            @Override
            protected JettyEmbeddedServletContainer getJettyEmbeddedServletContainer(Server server) {
                return super.getJettyEmbeddedServletContainer(server);
            }
        };
        factory.setUseForwardHeaders(true);
        factory.addServerCustomizers((JettyServerCustomizer) server -> {

            final QueuedThreadPool threadPool = server.getBean(QueuedThreadPool.class);
            threadPool.setMaxThreads(Integer.valueOf(maxThreads));
            threadPool.setMinThreads(Integer.valueOf(minThreads));
            threadPool.setIdleTimeout(Integer.valueOf(idleTimeout));

            for (Connector connector : server.getConnectors()) {
                if (connector instanceof ServerConnector) {
                    HttpConnectionFactory connectionFactory = ((ServerConnector) connector).getConnectionFactory(HttpConnectionFactory.class);
                    connectionFactory.getHttpConfiguration().setResponseHeaderSize(16 * 1024);
                }
            }
        });
        return factory;
    }
}
