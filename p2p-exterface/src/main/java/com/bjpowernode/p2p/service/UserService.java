package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.model.user.User;

/**
 * @author zhangguanle
 * @create 2019-01-16 16:04
 */
public interface UserService {

   Long queryAllUserCount();

   User queryUserByPhone(String phone);
}
