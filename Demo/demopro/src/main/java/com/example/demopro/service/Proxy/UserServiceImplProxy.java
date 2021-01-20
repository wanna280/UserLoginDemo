package com.example.demopro.service.Proxy;

import com.alibaba.fastjson.JSON;
import com.example.demopro.bean.UserBean;
import com.example.demopro.service.Impl.RedisServiceImpl;
import com.example.demopro.service.Impl.UserServiceImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class UserServiceImplProxy {
    @Resource
    RedisServiceImpl redisService;
    @Resource
    UserServiceImpl userService;

    //定义切入点
    @Pointcut("execution(* com.example.demopro.service.Impl.UserServiceImpl.GetUserByUserName(..))")
    public void point_cut_getUserByUserName() {

    }

    // 定义环绕通知，新增从Redis查询的功能
    @Around("point_cut_getUserByUserName()")
    public UserBean Around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();  //获取切入点方法的参数数组(Object[])
        String key = "user_" + (String) args[0]; //获取切入点方法的参数中的用户名，并添加前缀作为redis的key
        String value = "";   //初始化value，用来保存从Redis中查询出来的字符串(后续转换成JSON）

        //尝试从Redis缓存中获取对象，获取到就直接从Redis获取，否则就从数据库查询，并加入到Redis缓存当中
        try {
            value = redisService.get(key);  //从Redis中查询
        } catch (Exception ex) {
            value = "";
        }

        UserBean userBean;
        //如果Redis中没有这个对象，就在数据库中查询出来，并加入到Redis缓存当中
        if (value == null || value.equals("")) {  //必须先判断空再判断空字符串

            System.out.println("从数据库获取");
            //从数据库查询这个对象
            userBean = (UserBean) proceedingJoinPoint.proceed();

            //将对象的值存储到Redis缓存当中
            redisService.set(key, JSON.toJSONString(userBean));
        } else {
            System.out.println("从Redis中获取");

            //如果Redis缓存中已经有这个对象，就将其JSON转换为UserBean对象作为查询结果
            JSON json = (JSON) JSON.parse(value);
            userBean = JSON.toJavaObject(json, UserBean.class);  //将JSON转换为UserBean对象
        }

        return userBean;  //return
    }
}
