package com.task.bot.config;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpConfiguration {

    @Bean
    public CloseableHttpClient httpClient() {
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(
                        RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD)
                                .build()
                )
                .build();
    }
}
