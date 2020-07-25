package com.yhq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yhq.entity.Spu;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: YHQ
 * @Date: 2020/7/19 21:17
 */
@Slf4j
public class RedisCache implements Cache {

    private RedisTemplate<String,Object> redisTemplate;
    private ObjectMapper objectMapper;

    private final String id;

    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


    public RedisCache(final String id) {
        if (null == id || "".equals(id)) {
            throw new IllegalArgumentException("mybatis redis cache need an id.");
        }
        this.id = id;
        log.info("mybatis redis cache id: {}", id);
        redisTemplate = RedisUtil.redisTemplate;
        objectMapper = new ObjectMapper();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object o1) {
        if (null == key) {
            return;
        }
        log.info("mybatis redis cache put. K={} value={}", key, o1);
        try {
            // 放入
            redisTemplate.opsForValue().set(key.toString(),o1);
            // 记录放入的key
            redisTemplate.opsForList().leftPush(id,key.toString());
        } catch (Exception e) {
            log.error("mybatis redis cache put exception. K=" + key + " V=" + o1 + "", e);
        }
    }

    @Override
    public Object getObject(Object key) {
        if (null == key) {
            return null;
        }
        log.info("mybatis redis cache get. K={}", key);
        Object result = null;
        try {
            result = redisTemplate.opsForValue().get(key.toString());
        } catch (Exception e) {
            log.error("mybatis redis cache get exception. K=" + key + " V=" + result + "", e);
        }
        return result;
    }

    @Override
    public Object removeObject(Object key) {
        if (null == key) {
            return null;
        }
        log.info("mybatis redis cache remove. K={}", key);
        Object result = null;
        try {
            // 讲key设置为立即过期
            result = redisTemplate.expireAt(key.toString(), new Date());

            // 删除list 中的 key
            redisTemplate.opsForList().remove(id,1,key.toString());

        } catch (Exception e) {
            log.error("mybatis redis cache remove exception. " + key + " V=" + result + "", e);
        }
        return result;
    }

    @Override
    public void clear() {
        try {
            List range = redisTemplate.opsForList().range(id, 0, -1);
            if (range!=null){
                Date date = new Date();
                for (Object s : range) {
                    redisTemplate.expireAt(s.toString(),date);
                }

                redisTemplate.expireAt(id,date);
            }else {
                log.debug("clear cache is null");
            }

        }catch (Exception e){
            log.error("clear cache fail");
        }

    }

    @Override
    public int getSize() {
        Long l;
        if ((l = redisTemplate.getConnectionFactory().getConnection().dbSize())!=null){
            return Math.toIntExact(l);
        }

        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return lock;
    }
}
