package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.model.user.User;
import com.bjpowernode.p2p.model.vo.ResultObject;

/**
 * @author zhangguanle
 * @create 2019-01-16 16:04
 */
public interface UserService {

   /**
    * 查询平台总注册用户数量
    * @return
    */
   Long queryAllUserCount();

   User queryUserByPhone(String phone);
   //注册
   ResultObject registerUser(String phone,String password);
   //登录
   User queryUserByPhoneAndPassword(String phone,String password);

   int updateUserById(User user);
}
