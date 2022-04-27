package com.springcloud.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/redisson")
public class RedissonController {

    @Autowired(required = false)
    private RedissonClient redissonClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * redisson 分布式锁
     * 127.0.0.1:10802/redisson/testRedisson
     *
     * @return 结果
     */
    @RequestMapping("/testRedisson")
    public String testRedisson() {
        RLock lock = redissonClient.getLock("testRedissonLock");
        boolean locked = false;
        try {
            locked = lock.tryLock(0, 10, TimeUnit.SECONDS);
            if (locked) {
                log.info("--------------ok");
                return "ok.......................................";
            } else {
                log.info("获取锁异常.......................................");
                return "获取锁失败.......................................";
            }
        } catch (Exception e) {
            log.info("获取锁异常", e);
            return "获取锁异常.......................................";
        } finally {
            if (!locked) {
                log.info("获取锁失败.......................................");
            }
            lock.unlock();
        }
    }

    /**
     * 获取redis值
     * 127.0.0.1:10802/redisson/getRedisson?key=testRedissonLock
     *
     * @param key key
     * @return 结果
     */
    @RequestMapping("/getRedisson")
    public String getRedisson(String key) {
        String value = redisTemplate.opsForValue().get(key);
        return value;
    }

    /**
     * 存入redis key
     * 127.0.0.1:10802/redisson/setRedisson?key=testRedissonLock&value=aaa
     *
     * @param key   key
     * @param value value
     * @return 结果
     */
    @RequestMapping("/setRedisson")
    public Boolean setRedisson(String key, String value) {
        redisTemplate.opsForValue().set(key, value, 60, TimeUnit.SECONDS);
        return true;
    }

    /**
     * 删除redis key
     * 127.0.0.1:10802/redisson/delRedisson?key=testRedissonLock
     *
     * @param key key
     * @return 结果
     */
    @RequestMapping("/delRedisson")
    public Boolean delRedisson(String key) {
        Boolean delete = redisTemplate.delete(key);
        return delete;
    }

    /**
     * 有问题
     *
     * @param args
     */
    public static void main(String[] args) {
        // 默认redis://127.0.0.1:6379
        RedissonClient redisson = Redisson.create();
        RLock lock = redisson.getLock("testRedissonLock");
        try {
            Boolean locked = lock.tryLock(0, 10, TimeUnit.SECONDS);
            log.info("locked={}", locked);
        } catch (InterruptedException e) {
            log.info("异常", e);
        }
    }

}
