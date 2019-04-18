package com.bjpowernode.p2p.pay.controller;

import com.bjpowernode.p2p.constant.Constants;
import com.bjpowernode.p2p.model.loan.LoanInfo;
import com.bjpowernode.p2p.service.BidInfoService;
import com.bjpowernode.p2p.service.LoanInfoService;
import com.bjpowernode.p2p.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 首页信息展示
 * @author zhangguanle
 * @create 2019-01-15 0:51
 */
@Controller
public class IndexController {

    @Autowired
    private LoanInfoService loanInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private BidInfoService bidInfoService;


    @RequestMapping("index")
    public String index(Model model){
        //1.历史年化收益率
        Double  historyAverageRate=loanInfoService.queryHistoryAverageRate();
        model.addAttribute(Constants.HISTORY_AVERAGE_RATE,historyAverageRate);

       //2.查询平台总注册用户数量
        Long allUserCount=userService.queryAllUserCount();
        model.addAttribute(Constants.ALL_USER_COUNT,allUserCount);

        //3.获取平台累计投资金额
        Double allBidMoney=bidInfoService.queryAllBidMoney();
        model.addAttribute(Constants.ALL_BID_MONEY,allBidMoney);

      //产品名称，根据不同的产品类型查出不同的数量的数据
        Map<String,Object> paramMap=new HashMap<>();
        //页码：
            paramMap.put("currentPage",0);
            paramMap.put("pageSize",1);
        //新手1个
            paramMap.put("productType",Constants.PRODUCT_TYPE_X);//新手包
            //调用Service层
        List<LoanInfo> xLoanInfo=loanInfoService.queryLoanInfoListByProductType(paramMap);
        //优选4个
        paramMap.put("pageSize",4);
        paramMap.put("productType",Constants.PRODUCT_TYPE_U);
        List<LoanInfo> uLoanInfo=loanInfoService.queryLoanInfoListByProductType(paramMap);
        //散标8个
        paramMap.put("pageSize",8);
        paramMap.put("productType",Constants.PRODUCT_TYPE_S);
        List<LoanInfo> sLoanInfo=loanInfoService.queryLoanInfoListByProductType(paramMap);

        model.addAttribute("xLoanInfoList",xLoanInfo);
        model.addAttribute("uLoanInfoList",uLoanInfo);
        model.addAttribute("sLoanInfoList",sLoanInfo);

        return "index";
    }
}
