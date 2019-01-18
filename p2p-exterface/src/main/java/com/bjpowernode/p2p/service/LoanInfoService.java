package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.model.loan.LoanInfo;
import com.bjpowernode.p2p.model.vo.PaginatinoVO;

import java.util.HashMap;
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

    PaginatinoVO<LoanInfo>  queryLoanInfoByPage(HashMap<String,Object> paramMap);

    LoanInfo queryLoanInfoById(Integer id);
}
