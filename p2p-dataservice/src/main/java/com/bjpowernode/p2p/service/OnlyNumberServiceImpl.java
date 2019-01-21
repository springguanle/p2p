package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author zhangguanle
 * @create 2019-01-21 21:12
 */
@Service("onlyNumberServiceImpl")
public class OnlyNumberServiceImpl implements  OnlyNumberService {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    /*
    * 使用redis生成全局唯一数字
    * */
    @Override
    public Long getOnlyNumber() {
        //生成订单号的唯一性
        return redisTemplate.opsForValue().increment(Constants.ONLY_NUMBER,1);
    }
}
