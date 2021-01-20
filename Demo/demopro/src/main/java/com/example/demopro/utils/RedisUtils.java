package com.example.demopro.utils;

import com.example.demopro.DemoproApplication;
import org.omg.CORBA.portable.InputStream;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Component
public class RedisUtils {
    public Map YamlReadJedisConfigFromResource(String path) {
        Yaml yaml = new Yaml();
        Map map = (Map) yaml.load(DemoproApplication.class.getResourceAsStream(path));
        Map map2 = (Map) ((Map) map.get("jedis")).get("pool");  //得到配置文件
        return map2;
    }

    public Map YamlReadJedisConfig(String path) throws FileNotFoundException {
        Yaml yaml = new Yaml();  //创建yaml对象
        Map map = (Map) yaml.load(new FileInputStream(path));  //根据路径读取文件
        Map map2 = (Map) ((Map) map.get("jedis")).get("pool");  //得到配置文件
        return map2;
    }

    public Map LoadJedisConfig() {
        Map<String, Object> map = new HashMap<>();
        map.put("host", "47.107.157.20");
        map.put("password", 123456);
        map.put("port", 6379);
        map.put("timeout", 5000);
        map.put("db", 2);
        map.put("max-active", 5000);
        map.put("max-idle", 1000);
        map.put("max-wait", 5000);
        map.put("testOnBorrow", true);
        map.put("testOnReturn", true);
        return map;
    }
}
