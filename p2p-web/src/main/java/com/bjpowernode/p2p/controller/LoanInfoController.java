package com.bjpowernode.p2p.controller;

import com.bjpowernode.p2p.model.loan.BidInfo;
import com.bjpowernode.p2p.model.loan.LoanInfo;
import com.bjpowernode.p2p.model.vo.PaginatinoVO;
import com.bjpowernode.p2p.service.BidInfoService;
import com.bjpowernode.p2p.service.LoanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

/**
 * @author zhangguanle
 * @create 2019-01-16 19:28
 */
@Controller
public class LoanInfoController {

    @Autowired
    private LoanInfoService loanInfoService;

    @Autowired
    private BidInfoService bidInfoService;

    @RequestMapping("/loan/loan")
    public  String loan(Integer ptype, Integer currentPage, Model model){

        //判断当前页码是否为null
        if(currentPage ==null){
            currentPage=1;
        }
        HashMap<String,Object> paramMap=new HashMap<>();

        //产品类型
        if(ptype!=null){
            paramMap.put("productType",ptype);
        }
        //每页显示的数量。可以放到Constant类里面，统一使用。
            int pageSize=9;
        //当前页
        paramMap.put("currentPage",(currentPage-1)*pageSize);

        paramMap.put("pageSize",pageSize);
        //调用Service去查询,根据分页和产品条件去查询

        PaginatinoVO<LoanInfo> paginatinoVo=loanInfoService.queryLoanInfoByPage(paramMap);

        //计算总页数
        int totalPage=paginatinoVo.getTotal().intValue() / pageSize==0?(paginatinoVo.getTotal().intValue() / pageSize):(paginatinoVo.getTotal().intValue() / pageSize+1);

        //总记录数
        model.addAttribute("totalRows",paginatinoVo.getTotal());
        //总页数
        model.addAttribute("totalPage",totalPage);
        //集合
        model.addAttribute("loanInfoList",paginatinoVo.getDataList());
        //当前页
        model.addAttribute("currentPage",currentPage);
        //产品类型
        if(ptype!=null){
            model.addAttribute("productType",ptype);
        }
        return "loan";
    }

    @RequestMapping("/loan/loanInfo")
    public String loanInfo(Model model,@RequestParam(value = "id",required = true) Integer id){

       List<BidInfo> bidInfo=bidInfoService.queryBidByLoanId(id);
       LoanInfo loanInfo=loanInfoService.queryLoanInfoById(id);

        model.addAttribute("bidInfo",bidInfo);

        model.addAttribute("loanInfo",loanInfo);

        return "loanInfo";
    }
}
