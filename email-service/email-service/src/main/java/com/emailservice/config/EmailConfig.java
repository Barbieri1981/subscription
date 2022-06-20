package com.emailservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "mail-config")
public class EmailConfig {
    private String host;
    private String port;
    private String userName;
    private String passWord;
    private String from;
}
