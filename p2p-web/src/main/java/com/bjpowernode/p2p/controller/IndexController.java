package com.bjpowernode.p2p.controller;

import com.bjpowernode.p2p.constant.Constants;
import com.bjpowernode.p2p.service.LoanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 首页信息展示
 * @author zhangguanle
 * @create 2019-01-15 0:51
 */
@Controller
public class IndexController {

    @Autowired
   private LoanInfoService loanInfoService;


    @RequestMapping("index")
    public String index(Model model){
        //历史年化收益率
       Double  historyAverageRate=loanInfoService.queryHistoryAverageRate();
        model.addAttribute(Constants.HISTORY_AVERAGE_RATE,historyAverageRate);
        return "index";
    }
}
