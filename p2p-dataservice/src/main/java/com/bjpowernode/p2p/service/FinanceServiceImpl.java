package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.mapper.user.FinanceAccountMapper;
import com.bjpowernode.p2p.model.user.FinanceAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangguanle
 * @create 2019-01-20 16:47
 */
@Service("financeServiceImpl")
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private FinanceAccountMapper financeAccountMapper;

    @Override
    public FinanceAccount selectFinanceByUserId(int uid) {
        return financeAccountMapper.selectFinanceByUserId(uid);
    }
}
