package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.constant.Constants;
import com.bjpowernode.p2p.mapper.loan.LoanInfoMapper;
import com.bjpowernode.p2p.model.loan.LoanInfo;
import com.bjpowernode.p2p.model.vo.PaginatinoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        //修改key的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        Double  historyAverageRate= (Double) redisTemplate.opsForValue().get(Constants.HISTORY_AVERAGE_RATE);
        //先从缓冲中查
        if(historyAverageRate==null){

            historyAverageRate=loanInfoMapper.queryHistoryAverageRate();
            //放到缓存中
            redisTemplate.opsForValue().set(Constants.HISTORY_AVERAGE_RATE,historyAverageRate);
        }
        return historyAverageRate;
    }
    /*
    * 查询首页产品信息
    * */
    @Override
    public List<LoanInfo> queryLoanInfoListByProductType(Map<String, Object> paramap) {
        return loanInfoMapper.queryLoanInfoListByProductType(paramap);
    }

    @Override
    public PaginatinoVO<LoanInfo> queryLoanInfoByPage(HashMap<String, Object> paramMap) {
        PaginatinoVO<LoanInfo> paginatinoVO=new PaginatinoVO<>();

        //查询总数据量
        Long total=loanInfoMapper.selectTotal(paramMap);
        paginatinoVO.setTotal(total);

        List<LoanInfo> loanInfoList=loanInfoMapper.queryLoanInfoListByProductType(paramMap);

        paginatinoVO.setDataList(loanInfoList);

        return paginatinoVO;
    }

    @Override
    public LoanInfo queryLoanInfoById(Integer id) {
      return   loanInfoMapper.selectByPrimaryKey(id);
    }
}
