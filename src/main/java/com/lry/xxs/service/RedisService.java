package com.lry.xxs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    //字符串
    public void addString(String key, String value){
        redisTemplate.opsForValue().set(key,value);
    }

    public String getString(String key){
        return redisTemplate.opsForValue().get(key).toString();
    }

    //集合
    public void addSet(String key,Set<Object> set) {
        redisTemplate.opsForSet().add(key, set);
        // 获取
        Set<String> resultSet = redisTemplate.opsForSet().members(key);
        System.out.println("resultSet:" + resultSet);
    }
    public Set<Object> getSet(String key) {
        return (Set<Object>) redisTemplate.opsForSet().members(key);
    }

    //map
    public void addMap(String key, Map map){
        redisTemplate.opsForHash().putAll(key, map);
    }

    public Map getMap(String key){
        return (Map)redisTemplate.opsForHash().entries(key);
    }
}
