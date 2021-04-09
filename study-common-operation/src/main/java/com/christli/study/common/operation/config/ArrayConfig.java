package com.christli.study.common.operation.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "array-configs")
@Data
public class ArrayConfig {
    private List<Config> configs = new ArrayList<>();

    @Data
    public static class Config {

        private String name;
        private String desc;
    }
}