package com.bjpowernode.p2p.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.bjpowernode.p2p.constant.Constants;
import com.bjpowernode.p2p.model.loan.RechargeRecord;
import com.bjpowernode.p2p.model.user.FinanceAccount;
import com.bjpowernode.p2p.model.user.User;
import com.bjpowernode.p2p.model.vo.ResultObject;
import com.bjpowernode.p2p.service.FinanceService;
import com.bjpowernode.p2p.service.OnlyNumberService;
import com.bjpowernode.p2p.service.RechargeRecordService;
import com.bjpowernode.p2p.service.UserService;
import com.bjpowernode.p2p.util.DateUtils;
import com.bjpowernode.p2p.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;

/**
 * @author zhangguanle
 * @create 2019-01-18 21:01
 */
@Controller
public class UserController {
    @Autowired
    private RechargeRecordService rechargeRecordService;

    @Autowired
    private UserService  userService;

    @Value("${realNameAppkey}")
    private String realNameAppkey;

    @Value("${realNameUrl}")
    private String realNameUrl;

    @Autowired
    private FinanceService financeService;

    @Autowired
    private OnlyNumberService onlyNumberService;

    /*
    * 注册页面的跳转
    * */
    @RequestMapping("/loan/registerUI")
    public String registerUI(){
        return "register";
    }

    /*
    * 根据用户的手机号去查用户是否存在
    * */
    @RequestMapping("/loan/checkPhone")
    @ResponseBody
    public Object checkPhone(@RequestParam(value="phone",required = true) String phone){
        User user=userService.queryUserByPhone(phone);
        //将消息封装到map中
        HashMap<Object, Object> map = new HashMap<>();
        if(user!=null){
            map.put(Constants.ERROR_MESSAGE,"该手机号已经注册,请更换手机");
            return map;
        }
        map.put(Constants.ERROR_MESSAGE,Constants.OK);
        return map;
    }

    /*
    * 校验验证码
    * */
    @RequestMapping("/loan/checkCaptcha")
    @ResponseBody
    public Object checkCaptcha(HttpSession session,@RequestParam(value = "captcha",required = true) String captcha){
        String sessionCapthcha=(String)session.getAttribute(Constants.CAPTCHA);
        HashMap<Object, Object> map = new HashMap<>();
        if(!StringUtils.equalsIgnoreCase(captcha,sessionCapthcha)){
            map.put(Constants.ERROR_MESSAGE,"请输入正确的验证码");
            return map;
        }
        map.put(Constants.ERROR_MESSAGE,Constants.OK);
        return map;
    }

    //用户注册功能
    @RequestMapping("/loan/register")
    @ResponseBody
    public Object register(HttpSession session,@RequestParam(value = "phone",required = true)String phone,@RequestParam(value = "password",required = true)String password){
        //注册用户，手机号和密码通过方法传过去，在user表中新增一条数据，同时finamece表
        //增加一条数据，插入成功返回一个状态
        HashMap<String,Object> map=new HashMap<>();

        ResultObject ro=userService.registerUser(phone,password);

        if(!StringUtils.equals(Constants.SUCCESS,ro.getErrorCode())){
            map.put(Constants.ERROR_MESSAGE,"注册失败,请稍后重试");
        }
        User user=userService.queryUserByPhone(phone);
        //user信息放入到session
        session.setAttribute(Constants.SESSION_USER,user);
        //将账户信息放入到session中

        FinanceAccount financeAccount = financeService.selectFinanceByUserId(user.getId());

        session.setAttribute("financeAccount",financeAccount);
        //将Ok信息返回到前台
        map.put(Constants.ERROR_MESSAGE,Constants.OK);

        return map;
    }

    //实名认证
    @RequestMapping("/loan/verifyRealName")
    @ResponseBody
    public Object verifyName(HttpSession session,@RequestParam(value = "idCard",required = true) String idCard,@RequestParam(value = "realName",required = true) String realName){
        //返回结果的map
        HashMap<String, Object> resultMap = new HashMap<>();

        //参数相关map
        HashMap<String, Object> paramMap = new HashMap<>();

        //实名认证的appkey
        paramMap.put("appkey",realNameAppkey);

        //真实姓名
        paramMap.put("name", realName);
        //身份证号
        paramMap.put("certNo", idCard);

        //调用httpclient方法验证
        String result = HttpClientUtil.doPost(realNameUrl, paramMap);
        //将字符串转换为json对象
        JSONObject jsonObject = JSONObject.parseObject(result);

        //获取指定key的值
        String code = jsonObject.getString("code");

        //判断通信是否成功
        if (StringUtils.equals("10000", code)) {
            //通信成功
            Boolean isok = jsonObject.getJSONObject("result").getBoolean("success");
            //判断结果
            if (isok){
                //用户输入了正确的姓名和身份证号
                //从session中获取用户对象数据
                User sessionUser = (User)session.getAttribute(Constants.SESSION_USER);

                //更新用户信息
                User user = new User();
                user.setId(sessionUser.getId());
                user.setName(realName);
                user.setIdCard(idCard);

                int updateUserCount = userService.updateUserById(user);

                if (updateUserCount > 0) {
                    //更新session中的用户信息
                    sessionUser.setName(realName);
                    sessionUser.setIdCard(idCard);
                    session.setAttribute(Constants.SESSION_USER,sessionUser);

                    resultMap.put(Constants.ERROR_MESSAGE, Constants.OK);
                }else{
                    resultMap.put(Constants.ERROR_MESSAGE, "系统忙，请稍后再试...");
                    return resultMap;
                }

            }else {
                resultMap.put(Constants.ERROR_MESSAGE,"真实姓名与身份证号码不匹配");
                return resultMap;
            }
        }else {
            resultMap.put(Constants.ERROR_MESSAGE,"通信失败，请稍后再试");
            return resultMap;
        }

        return resultMap;
    }

    /*
    * 用户登录
    * */
    @RequestMapping("/loan/login")
    @ResponseBody
    public Object login(HttpSession session,@RequestParam(value = "phone",required = true) String phone,@RequestParam(value = "password",required = true) String password) {
        HashMap<String, Object> resultMap = new HashMap<>();

        //将手机号和密码传入数据库做匹配查询
        User user = userService.queryUserByPhoneAndPassword(phone,password);

        //判断用户是否存在
        if (user == null) {
            resultMap.put(Constants.ERROR_MESSAGE, "用户名或密码错误");
            return  resultMap;
        }

        //将用户对象放到session中
        session.setAttribute(Constants.SESSION_USER,user);

        //将账户信息放到session中
        FinanceAccount financeAccount = financeService.selectFinanceByUserId(user.getId());
        session.setAttribute("financeAccount",financeAccount);

        resultMap.put(Constants.ERROR_MESSAGE, Constants.OK);

        return resultMap;

    }

    /*
    * 我的小金库
    * */
    @RequestMapping("/loan/myCenter")
    public String myCenter(){
        return "myCenter";
    }

    @RequestMapping("/loan/toRecharge")
    public String toAlipRecharge(Model model, HttpSession session, @RequestParam(value = "rechargeMoney",required = true) Double rechargeMoney){
        User sessionUser=(User)session.getAttribute(Constants.SESSION_USER);
        String  rechargeNumber=DateUtils.getTimeStamp()+onlyNumberService.getOnlyNumber();

        RechargeRecord rechargeRecord = new RechargeRecord();

        rechargeRecord.setUid(sessionUser.getId());
        rechargeRecord.setRechargeNo(rechargeNumber);
        rechargeRecord.setRechargeDesc("支付宝");
        rechargeRecord.setRechargeMoney(rechargeMoney);
        rechargeRecord.setRechargeStatus("0");
        rechargeRecord.setRechargeTime(new Date());
        int rechargeCount=rechargeRecordService.addRechargeRecord(rechargeRecord);
        if(rechargeCount>0){

        }else{
            model.addAttribute("trade_msg","充值人数过多，稍后再试");
        }
        return "toRchargeBack";
    }
}
