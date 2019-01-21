package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.model.user.FinanceAccount;

/**
 * @author zhangguanle
 * @create 2019-01-20 16:45
 */
public interface FinanceService {

    FinanceAccount selectFinanceByUserId(int uid);
}
