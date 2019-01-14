package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.mapper.loan.LoanInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangguanle
 * @create 2019-01-15 1:15
 */
@Service
public class LoanInfoServiceImpl implements LoanInfoService {

    @Autowired
    private LoanInfoMapper loanInfoMapper;

    @Override
    public Double queryHistoryAverageRate() {
        Double historyAverageRate=loanInfoMapper.queryHistoryAverageRate();
        return historyAverageRate;
    }
}
