package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.constant.Constants;
import com.bjpowernode.p2p.mapper.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangguanle
 * @create 2019-01-16 16:05
 */
@Service("userServiceImpl")
public class UserServiceImpl implements  UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    /*
    *   获取注册用户数量
    * */
    @Override
    public Long queryAllUserCount() {
        //设置key的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //获取指定操作某个key的对象
        BoundValueOperations<Object, Object> ops = redisTemplate.boundValueOps(Constants.ALL_USER_COUNT);
        //从缓冲中查
        Object allUserCount = ops.get();

        if(allUserCount==null){
            allUserCount=userMapper.selectAllUserCount();
            //设置失效时间
            ops.set(allUserCount,15, TimeUnit.SECONDS);
        }
        return (Long) allUserCount;
    }
}
