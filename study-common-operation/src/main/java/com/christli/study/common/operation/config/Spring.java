package com.christli.study.common.operation.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = {"classpath:test.yml"}, encoding = "gbk", factory = YmlConfigFactory.class)
@ConfigurationProperties(prefix = "spring")
public class Spring {

    private String value;
    private String remark;
}
