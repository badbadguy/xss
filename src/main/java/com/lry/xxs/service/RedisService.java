package com.lry.xxs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    //字符串
    public void addString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, 1, TimeUnit.HOURS);
    }

    public String getString(String key) {
        return redisTemplate.opsForValue().get(key).toString();
    }

    //检查key是否存在
    public Boolean checkKey(String key) {
        return redisTemplate.hasKey(key);
    }

    //集合
    public void addSet(String key, Set<Object> set) {
        redisTemplate.opsForSet().add(key, set);
        redisTemplate.expire(key, 1, TimeUnit.HOURS);
    }

    public Set<Object> getSet(String key) {
        return (Set<Object>) redisTemplate.opsForSet().members(key);
    }

    //map
    public void addMap(String key, Map map) {
        redisTemplate.opsForHash().putAll(key, map);
        redisTemplate.expire(key, 1, TimeUnit.HOURS);
    }

    public Map getMap(String key) {
        return (Map) redisTemplate.opsForHash().entries(key);
    }

    //list
    public void addList(String key, String value) {
        redisTemplate.opsForList().leftPush(key, value);
        //设置当天凌晨清空
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        redisTemplate.expire(key,(cal.getTimeInMillis() - System.currentTimeMillis()) / 1000,TimeUnit.SECONDS);
    }

    public void addListAll(String key, String[] value) {
        redisTemplate.opsForList().leftPushAll(key, value);
        //设置当天凌晨清空
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        redisTemplate.expire(key, (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000, TimeUnit.SECONDS);
    }

    public Long sizeList(String key) {
        return redisTemplate.opsForList().size(key);
    }

    public void removeList(String key, long count, String value) {
        redisTemplate.opsForList().remove(key, count, value);
    }

    public String getListOne(String key, long index) {
        return (String) redisTemplate.opsForList().index(key, index);
    }
}
