package com.fawry.user_api.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "custom.datasource")
@Getter
@Setter
public class DatasourceProperties {
    private String driver;
    private String url;
    private String username;
    private String password;
    private int maxPoolSize;
    private int maxLifeTime;

}

