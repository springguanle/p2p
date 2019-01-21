package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.model.user.User;
import com.bjpowernode.p2p.model.vo.ResultObject;

/**
 * @author zhangguanle
 * @create 2019-01-16 16:04
 */
public interface UserService {

   Long queryAllUserCount();

   User queryUserByPhone(String phone);
   //注册
   ResultObject registerUser(String phone,String password);
   //登录
   User queryUserByPhoneAndPassword(String phone,String password);

   int updateUserById(User user);
}
