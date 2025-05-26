package com.bes.test.config;

import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;
import reactor.netty.http.Http3SslContextSpec;
import reactor.netty.http.HttpProtocol;

import javax.net.ssl.KeyManagerFactory;
import java.io.InputStream;
import java.security.KeyStore;
import java.time.Duration;

@Component
class TestWebServerCustomizer implements WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {

    @Override
    public void customize(NettyReactiveWebServerFactory factory) {
        factory.addServerCustomizers(server -> {

            KeyStore keyStore = null;
            KeyManagerFactory kmf = null;
            try {
                keyStore = KeyStore.getInstance("PKCS12");
                try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("keystore.p12")) {
                    keyStore.load(is, "changeit".toCharArray());
                }
                kmf = KeyManagerFactory.getInstance(
                        KeyManagerFactory.getDefaultAlgorithm()
                );
                kmf.init(keyStore, "changeit".toCharArray());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


            Http3SslContextSpec sslContextSpec =
                    Http3SslContextSpec.forServer(kmf, "changeit");

            return server
                    .protocol(HttpProtocol.H2)
                    .protocol(HttpProtocol.HTTP3)
                    .secure(spec -> spec.sslContext(sslContextSpec))
                    .http3Settings(spec -> spec.idleTimeout(Duration.ofSeconds(5))
                            .maxData(10_000_000)
                            .maxStreamDataBidirectionalRemote(1_000_000)
                            .maxStreamsBidirectional(100));
        });
    }
}
