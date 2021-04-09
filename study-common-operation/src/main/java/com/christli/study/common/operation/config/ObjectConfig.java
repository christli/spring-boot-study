package com.christli.study.common.operation.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "configs.config")
@Data
public class ObjectConfig {

    private String name;
    private String desc;
}
