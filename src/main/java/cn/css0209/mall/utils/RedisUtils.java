package cn.css0209.mall.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Redis操作
 * @author blankyk
 */
@Component
public class RedisUtils {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 获取 value
     * @param key key
     * @return
     */
    String get(String key) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置key-value
     * @param key key
     * @param value value
     */
    void set(String key, String value) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        if (StringUtils.isEmpty(key) || value == null) {
            return;
        }
        //3天过期
        redisTemplate.opsForValue().set(key, value,3, TimeUnit.DAYS);
    }

    /**
     * 删除key-value
     * @param key key
     * @return
     */
    boolean del(String key){
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        if (StringUtils.isEmpty(key)) {
            return false;
        }else{
            return redisTemplate.opsForValue().getOperations().delete(key);
        }
    }

    /**
     * 心跳机制
     */
    @Scheduled(cron = "0/5 * * * * *")
    public void timer() {
        redisTemplate.opsForValue().get("heartbeat");
    }
}