package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.model.loan.BidInfo;

import java.util.List;

/**
 * 金额业务
 * @author zhangguanle
 * @create 2019-01-16 16:25
 */
public interface BidInfoService {
    /**
     * 查询平台累计投资金额
     * @return
     */
    Double queryAllBidMoney();

    List<BidInfo> queryBidByLoanId(Integer id);
}
