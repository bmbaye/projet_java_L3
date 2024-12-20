package com.file_rouge.core.Service.impl;

import java.io.InputStream;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;
import com.file_rouge.core.Service.YamlService;

public class YamlServiceImpl implements YamlService{

    private String path = "application.yaml";

    @Override
    public Map<String, Object> loadYaml() {
        return this.loadYaml(path);
    }

    @Override
    public Map<String, Object> loadYaml(String path) {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
        .getClassLoader()
        .getResourceAsStream(path);
        return yaml.load(inputStream);
    }
    
}
