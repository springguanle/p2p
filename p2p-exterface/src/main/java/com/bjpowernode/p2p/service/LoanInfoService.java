package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.model.loan.LoanInfo;

import java.util.List;
import java.util.Map;

/**
 * @author zhangguanle
 * @create 2019-01-15 1:04
 */

public interface LoanInfoService {
    //查询率
    Double queryHistoryAverageRate();
    //查询产品列表
    List<LoanInfo> queryLoanInfoListByProductType(Map<String,Object> paramap);

}
