package com.rbkmoney.dark.api.config;

import com.rbkmoney.damsel.questionary_proxy_aggr.QuestionaryAggrProxyHandlerSrv;
import com.rbkmoney.dark.api.config.property.QuestionaryAggrProxyProperties;
import com.rbkmoney.woody.thrift.impl.http.THSpawnClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class QuestionaryAggrProxyConfig {

    private final QuestionaryAggrProxyProperties questionaryAggrProxyProperties;

    @Bean
    public QuestionaryAggrProxyHandlerSrv.Iface questionaryAggrProxyHandler() throws IOException {
        return new THSpawnClientBuilder()
                .withAddress(questionaryAggrProxyProperties.getUrl().getURI())
                .withNetworkTimeout(questionaryAggrProxyProperties.getNetworkTimeout())
                .build(QuestionaryAggrProxyHandlerSrv.Iface.class);

    }

}