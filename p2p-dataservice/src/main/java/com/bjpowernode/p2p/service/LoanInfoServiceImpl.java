package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.constant.Constants;
import com.bjpowernode.p2p.mapper.loan.LoanInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

/**
 * @author zhangguanle
 * @create 2019-01-15 1:15
 */
@Service("loanInfoServiceImpl")
public class LoanInfoServiceImpl implements LoanInfoService {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    private LoanInfoMapper loanInfoMapper;

    @Override
    public Double queryHistoryAverageRate() {
        //先从缓冲中取出
        //修改key的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        Double  historyAverageRate= (Double) redisTemplate.opsForValue().get(Constants.HISTORY_AVERAGE_RATE);

        if(historyAverageRate==null){
            historyAverageRate=loanInfoMapper.queryHistoryAverageRate();
            //放到缓存中
            redisTemplate.opsForValue().set(Constants.HISTORY_AVERAGE_RATE,historyAverageRate);
        }

        return historyAverageRate;
    }
}
