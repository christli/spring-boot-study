package com.christli.study.common.operation.controller;

import com.christli.study.common.operation.config.ArrayConfig;
import com.christli.study.common.operation.config.ObjectConfig;
import com.christli.study.common.operation.config.Spring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ConfigController {

    @Value("${config.name}")
    private String name;

    @Value("${config.desc}")
    private String desc;

    @Autowired
    private ObjectConfig objectConfig;

    @Autowired
    private ArrayConfig arrayConfig;

    @Autowired
    private Spring spring;

    @GetMapping("getValue")
    public String getValue() {
        return "name=" + name + ";desc=" + desc;
    }

    @GetMapping("/getConfig")
    public String getConfig() {
        return objectConfig.getName() + " ;" + objectConfig.getDesc();
    }

    @GetMapping("/getConfigs")
    public String getConfigs() {
        String content = "";
        List<ArrayConfig.Config> configList = arrayConfig.getConfigs();
        Map<String, Object> map = new HashMap<>();
        for (ArrayConfig.Config bean : configList) {
            content += bean.getName() + " ;" + bean.getDesc() + " ，";
        }
        return content;
    }

    @GetMapping("getSource")
    public String getSource() {
        return spring.getRemark() + " ;" + spring.getValue();
    }
}
