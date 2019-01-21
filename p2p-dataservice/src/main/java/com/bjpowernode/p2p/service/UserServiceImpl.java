package com.bjpowernode.p2p.service;

import com.bjpowernode.p2p.constant.Constants;
import com.bjpowernode.p2p.mapper.user.FinanceAccountMapper;
import com.bjpowernode.p2p.mapper.user.UserMapper;
import com.bjpowernode.p2p.model.user.FinanceAccount;
import com.bjpowernode.p2p.model.user.User;
import com.bjpowernode.p2p.model.vo.ResultObject;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangguanle
 * @create 2019-01-16 16:05
 */
@Service("userServiceImpl")
public class UserServiceImpl implements  UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FinanceAccountMapper financeAccountMapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    /*
    *   获取注册用户数量
    * */
    @Override
    public Long queryAllUserCount() {
        //设置key的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //获取指定操作某个key的对象
        BoundValueOperations<Object, Object> ops = redisTemplate.boundValueOps(Constants.ALL_USER_COUNT);
        //从缓冲中查
        Object allUserCount = ops.get();

        if(allUserCount==null){
            allUserCount=userMapper.selectAllUserCount();
            //设置失效时间
            ops.set(allUserCount,15, TimeUnit.SECONDS);
        }
        return (Long) allUserCount;
    }

    /*
    * 校验用户是否存在，根据手机号码是否存在
    * */
    @Override
    public User queryUserByPhone(String phone) {
        return userMapper.queryUserByPhone(phone);
    }

    /*
    * 用户注册
    * */
    @Override
    public ResultObject registerUser(String phone, String password) {
        ResultObject resultObject = new ResultObject();
        //先将结果设置为成功
        resultObject.setErrorCode(Constants.SUCCESS);
        User user=new User();
        user.setPhone(phone);
        user.setLoginPassword(password);
        user.setAddTime(new Date());
        user.setLastLoginTime(new Date());
        int insertUser=userMapper.insert(user);
        //新增finance表数据
        if(insertUser>0){//插入成功
            //将userId查出来
            User userInfo = userMapper.queryUserByPhone(phone);
            FinanceAccount financeAccount= new FinanceAccount();
            financeAccount.setUid(userInfo.getId());
            //初始化用户的账户
            financeAccount.setAvailableMoney(888.0);
            int i = financeAccountMapper.insert(financeAccount);
            if (i<0){
                //余额表注册失败了
                resultObject.setErrorCode(Constants.FAIL);
            }
        }else {
            //user表注册失败
            // 插入失败
            resultObject.setErrorCode(Constants.FAIL);
        }

        return resultObject;
    }
    /*
    * 用户登录
    * */
    @Override
    public User queryUserByPhoneAndPassword(String phone, String password) {

        User user=userMapper.queryUserByPhoneAndPassword(phone,password);
        //判断用户是否存在
        if(user!=null){
            //用户登录时间
           user.setLastLoginTime(new Date());
            userMapper.updateByPrimaryKey(user);
        }
        return user;
    }

    @Override
    public int updateUserById(User user) {
        int i = userMapper.updateByPrimaryKey(user);

        return i;
    }
}
