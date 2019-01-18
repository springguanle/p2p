package com.bjpowernode.p2p.controller;

import com.alibaba.druid.util.StringUtils;
import com.bjpowernode.p2p.constant.Constants;
import com.bjpowernode.p2p.model.user.User;
import com.bjpowernode.p2p.service.UserService;
import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author zhangguanle
 * @create 2019-01-18 21:01
 */
@Controller
public class UserController {
    @Autowired
    private UserService  userService;

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
        if(!StringUtils.equals(captcha,sessionCapthcha)){
            map.put(Constants.ERROR_MESSAGE,"请输入正确的验证码");
            return map;
        }
        map.put(Constants.ERROR_MESSAGE,Constants.OK);
        return map;
    }

    //用户注册功能
   /* @RequestMapping("/loan/register")
    @ResponseBody
    public Object register(User user){
        HashMap<Object, Object> map = new HashMap<>();

        Integer nums=userService.registerUser(user);

        if(nums>0){
            map.put(Constants.ERROR_MESSAGE,"")
        }
        return map;
    }*/

}
