package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.mapper.loan.RechargeRecordMapper;
import com.bjpowernode.p2p.model.loan.RechargeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangguanle
 * @create 2019-01-21 21:19
 */
@Service("rechargeRecordServiceImpl")
public class RechargeRecordServiceImpl implements RechargeRecordService {

    @Autowired
    private RechargeRecordMapper rechargeRecordMapper;

    @Override
    public int addRechargeRecord(RechargeRecord rechargeRecord) {

        return  rechargeRecordMapper.insertSelective(rechargeRecord);
    }
}
