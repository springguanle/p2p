package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.model.loan.BidInfo;

import java.util.List;

/**
 * @author zhangguanle
 * @create 2019-01-16 16:25
 */
public interface BidInfoService {

    Double queryAllBidMoney();

    List<BidInfo> queryBidByLoanId(Integer id);
}
