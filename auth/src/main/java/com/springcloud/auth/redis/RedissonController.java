package com.springcloud.auth.redis;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    // -------------------------------------------string---------------------------------------------------------------

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
     * redis 命令
     * get key
     * @param key key
     * @return 结果
     */
    @RequestMapping("/delRedisson")
    public Boolean delRedisson(String key) {
        Boolean delete = redisTemplate.delete(key);
        return delete;
    }

    // -------------------------------------------string---------------------------------------------------------------

    // -------------------------------------------Hash---------------------------------------------------------------

    /**
     * 127.0.0.1:10802/redisson/hashRedisson
     *
     * redis 命令
     * hgetall userInfo  查询全部
     * hget userInfo name 查看userInfo中的name
     */
    @RequestMapping("/hashRedisson")
    public Map<Object, Object> hashRedisson() {
        Map userInfo = new HashMap<>();

        userInfo.put("name", "Lucy");
        userInfo.put("description", "a cool girl");

        redisTemplate.delete("userInfo1");

        //put ： (map的key, hashkey（name等），value
        redisTemplate.opsForHash().put("userInfo1", "sex", "Female");

        redisTemplate.delete("userInfo");

        //putAll：放入map对象
        redisTemplate.opsForHash().putAll("userInfo", userInfo);

        //values参数key对应的map值，返回结果为List<Object> ，keys方法也类似
        System.err.println(redisTemplate.opsForHash().values("userInfo").toString());

        //delete删除对应的字段
        redisTemplate.opsForHash().delete("userInfo", "name");

        //确定字段是否在map中存在，bool类型
        Boolean is_save = redisTemplate.opsForHash().hasKey("userInfo", "name");

        //multiGet,获取多个值
        Collection<Object> keys = new ArrayList<>();
        keys.add("name");
        keys.add("sex");
        System.out.println(redisTemplate.opsForHash().multiGet("userInfo", keys));

        Map<Object, Object> userInfo1 = redisTemplate.opsForHash().entries("userInfo");
        log.info("userInfo1={}", userInfo1);
        return userInfo1;

    }
    // -------------------------------------------Hash---------------------------------------------------------------

    // -------------------------------------------List---------------------------------------------------------------

    /**
     * 127.0.0.1:10802/redisson/listRedisson
     *
     * redis 命令
     * lrange userInfo 0 -1  查询userInfo全部数据
     */
    @RequestMapping("/listRedisson")
    public List<String> listRedisson() {
        List userInfo = new ArrayList();

        redisTemplate.delete("userInfo");
        userInfo.add("Nancy");
        userInfo.add("a sunny girl");
        redisTemplate.opsForList().leftPush("userInfo", "first String before nancy");
        redisTemplate.opsForList().leftPush("userInfo", "second String before nancy");
        //左右插入l/rpush , all 是集合
        redisTemplate.opsForList().rightPushAll("userInfo", userInfo);

        redisTemplate.opsForList().leftPop("userInfo");

        System.err.println(redisTemplate.opsForList().leftPop("userInfo").toString());
        System.err.println(redisTemplate.opsForList().rightPop("userInfo").toString());
        System.err.println("在出栈后的userInfoList列表数据：" + redisTemplate.opsForList().range("userInfo", 0, -1));

        List<String> userInfo1 = redisTemplate.opsForList().range("userInfo", 0, 2);
        log.info("userInfo1={}", userInfo1);
        return userInfo1;
    }

    // -------------------------------------------List---------------------------------------------------------------

    // -------------------------------------------Set---------------------------------------------------------------

    /**
     * 127.0.0.1:10802/redisson/setRedissonSet
     *
     * redis命令
     * smembers userInfoSet 查询userInfoSet全部数据
     * @return 结果
     */
    @RequestMapping("/setRedissonSet")
    public Set<String> setRedissonSet() {
        redisTemplate.delete("userInfoSet");
        // 移除set
        Long userInfoSet = redisTemplate.opsForSet().remove("userInfoSet", "cat");
        // 添加set
        Long add = redisTemplate.opsForSet().add("userInfoSet", "cat", "boy", "cat");
        // 计算集合大小
        Long userInfoSet1 = redisTemplate.opsForSet().size("userInfoSet");
        log.info("userInfoSet1大小={}", userInfoSet1);
        // 判断set中是否存在cat数据
        Boolean member = redisTemplate.opsForSet().isMember("userInfoSet", "cat");
        log.info("是否存在cat={}", member);
        // 随机获取set中一个元素
        String randomMember = redisTemplate.opsForSet().randomMember("userInfoSet");
        log.info("随机获取元素={}", randomMember);
        // 随机获取set中指定个数的元素
        List<String> userInfoSetList = redisTemplate.opsForSet().randomMembers("userInfoSet", 2);
        log.info("随机获取指定个数元素={}", userInfoSetList);
        // 获取set中所有元素
        Set<String> userInfoSet2 = redisTemplate.opsForSet().members("userInfoSet");
        log.info("获取set中所有元素={}", userInfoSet2);
        return userInfoSet2;
    }
    // -------------------------------------------Set---------------------------------------------------------------

    // -------------------------------------------ZSet---------------------------------------------------------------

    /**
     * 127.0.0.1:10802/redisson/zsetRedissonSet
     *
     * redis命令
     * zrange userInfoZSet 0 -1 查询userInfoZSet全部数据
     */
    @RequestMapping("/zsetRedissonSet")
    public void zsetRedissonSet() {
        redisTemplate.delete("userInfoZSet");
        Boolean add = redisTemplate.opsForZSet().add("userInfoZSet", "aa", 1);
        log.info("zet add={}", add);
    }
    // -------------------------------------------ZSet---------------------------------------------------------------

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
