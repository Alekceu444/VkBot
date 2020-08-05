package com.task.bot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bot")
public class BotProperties {

    private String token;
    private String apiVersion;
    private String confirmation;

    public String getToken() {
        return token;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }
}

