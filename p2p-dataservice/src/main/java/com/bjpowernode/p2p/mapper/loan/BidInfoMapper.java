package com.bjpowernode.p2p.mapper.loan;

import com.bjpowernode.p2p.model.loan.BidInfo;
import com.bjpowernode.p2p.model.loan.LoanInfo;

import java.util.List;
import java.util.Map;

public interface BidInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bid_info
     *
     * @mbggenerated Mon Sep 17 09:12:20 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bid_info
     *
     * @mbggenerated Mon Sep 17 09:12:20 CST 2018
     */
    int insert(BidInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bid_info
     *
     * @mbggenerated Mon Sep 17 09:12:20 CST 2018
     */
    int insertSelective(BidInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bid_info
     *
     * @mbggenerated Mon Sep 17 09:12:20 CST 2018
     */
    BidInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bid_info
     *
     * @mbggenerated Mon Sep 17 09:12:20 CST 2018
     */
    int updateByPrimaryKeySelective(BidInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table b_bid_info
     *
     * @mbggenerated Mon Sep 17 09:12:20 CST 2018
     */
    int updateByPrimaryKey(BidInfo record);

    Double selectAllBidMoney();

}