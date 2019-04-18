package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.constant.Constants;
import com.bjpowernode.p2p.mapper.loan.BidInfoMapper;
import com.bjpowernode.p2p.model.loan.BidInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 投资信息
 * @author zhangguanle
 * @create 2019-01-16 16:26
 */
@Service("bidInfoServiceImpl")
public class BidInfoServiceImpl implements  BidInfoService {

    /**
     * 注入dao层
     */
    @Autowired
    private BidInfoMapper bidInfoMapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    /**
     *查询投资的总金额
     * @return
     */
    @Override
    public Double queryAllBidMoney() {
        //1.修改key的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //2.获取指定key的值
        BoundValueOperations<Object, Object> ops = redisTemplate.boundValueOps(Constants.ALL_BID_MONEY);

        Object allBidMoney = ops.get();

        if(allBidMoney==null){
            allBidMoney=bidInfoMapper.selectAllBidMoney();
            //放到缓冲中
            ops.set(allBidMoney,10, TimeUnit.MINUTES);
        }
        return (Double) allBidMoney;
    }

    @Override
    public List<BidInfo> queryBidByLoanId(Integer id) {

        return bidInfoMapper.queryBidByLoanId(id);
    }
}
