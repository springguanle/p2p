package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.constant.Constants;
import com.bjpowernode.p2p.mapper.loan.BidInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangguanle
 * @create 2019-01-16 16:26
 */
@Service("bidInfoServiceImpl")
public class BidInfoServiceImpl implements  BidInfoService {

    @Autowired
    private BidInfoMapper bidInfoMapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public Double queryAllBidMoney() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        BoundValueOperations<Object, Object> ops = redisTemplate.boundValueOps(Constants.ALL_BID_MONEY);
        Object allBidMoney = ops.get();

        if(allBidMoney==null){
            allBidMoney=bidInfoMapper.selectAllBidMoney();
            //放到缓冲中
            ops.set(allBidMoney,10, TimeUnit.MINUTES);
        }
        return (Double) allBidMoney;
    }
}
