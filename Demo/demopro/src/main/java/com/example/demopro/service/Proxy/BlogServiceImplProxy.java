package com.example.demopro.service.Proxy;

import com.alibaba.fastjson.JSON;
import com.example.demopro.bean.BlogBean;
import com.example.demopro.service.RedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@EnableAspectJAutoProxy
public class BlogServiceImplProxy {
    @Resource
    RedisService redisService;

    @Pointcut("execution(* com.example.demopro.service.Impl.BlogServiceImpl.GetBlogsByUserName(..))")
    public void point_cut_GetBlogsByUserName() {

    }

    @Pointcut("execution(* com.example.demopro.service.Impl.BlogServiceImpl.GetBlogById(..))")
    public void point_cut_GetBlogsById() {

    }

    @Pointcut("execution(* com.example.demopro.service.Impl.BlogServiceImpl.GetAllBlogs())")
    public void point_cut_GetAllBlogs() {

    }

    @Around("point_cut_GetBlogsByUserName()")
    public ArrayList<BlogBean> Around_GetBlogsByUserName(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ArrayList<BlogBean> blogBeans = new ArrayList<>();
        String key = "blog_username_" + proceedingJoinPoint.getArgs()[0];
        List<String> values;
        try {
            long start = 0;
            long end = 20;
            values = redisService.lrange(key, start, end);
        } catch (Exception ex) {
            values = null;
        }
        //如果values为空，从数据库中查询
        if (values == null || values.size() == 0) {
            //System.out.println("从数据库中查询");
            blogBeans = (ArrayList<BlogBean>) proceedingJoinPoint.proceed();
            for (BlogBean blogBean :
                    blogBeans) {
                String json = JSON.toJSONString(blogBean);
                redisService.lpush(key, json);
            }
            redisService.setExpire(key, 60 * 60);
            System.out.println("从数据库中获取ByName");
        } else {//如果value不为空
            //System.out.println("从Redis中查询");
            for (String item :
                    values) {
                JSON json = (JSON) JSON.parse(item);
                BlogBean blogBean = JSON.toJavaObject(json, BlogBean.class);
                blogBeans.add(blogBean);
            }
            System.out.println("从Redis中获取ByName");
        }

        return blogBeans;
    }

    @Around("point_cut_GetBlogsById()")
    public BlogBean Around_GetBlogById(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //获取切入点方法的参数中的用户名，并添加前缀作为redis的key
        //value用来保存从Redis中查询出来的字符串(后续转换成JSON）
        String key = "blog_id_" + proceedingJoinPoint.getArgs()[0];
        String value;
        BlogBean blogBean;   //Bean

        //尝试从Redis缓存中获取对象，获取到就直接从Redis获取，否则就从数据库查询，并加入到Redis缓存当中
        try {
            value = redisService.get(key);  //从Redis中查询
        } catch (Exception ex) {
            value = "";
        }
        //如果Redis中没有这个对象，就在数据库中查询出来，并加入到Redis缓存当中
        if (value == null || value.equals("")) {  //必须先判断空再判断空字符串
            //从数据库查询这个对象
            blogBean = (BlogBean) proceedingJoinPoint.proceed();
            //将对象的值存储到Redis缓存当中
            redisService.set(key, JSON.toJSONString(blogBean));
            redisService.setExpire(key, 60 * 60);
            System.out.println("从数据库中获取ById");
        } else {
            //如果Redis缓存中已经有这个对象，就将其JSON转换为UserBean对象作为查询结果
            JSON json = (JSON) JSON.parse(value);
            blogBean = JSON.toJavaObject(json, BlogBean.class);  //将JSON转换为UserBean对象
            System.out.println("从Redis中获取ById");
        }

        return blogBean;  //return
    }

    @Around("point_cut_GetAllBlogs()")
    public ArrayList<BlogBean> Around_GetAllBlogs(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        ArrayList<BlogBean> blogBeans = new ArrayList<>();
        String key = "blog_all";
        List<String> values;
        try {
            long start = 0;
            long end = 20;
            values = redisService.lrange(key, start, end);
        } catch (Exception ex) {
            values = null;
        }
        //如果values为空，从数据库中查询
        if (values == null || values.size() == 0) {
            //System.out.println("从数据库中查询");
            blogBeans = (ArrayList<BlogBean>) proceedingJoinPoint.proceed();
            for (BlogBean blogBean :
                    blogBeans) {
                String json = JSON.toJSONString(blogBean);
                redisService.lpush(key, json);
            }
            redisService.setExpire(key, 60 * 60);
            System.out.println("从数据库中获取GetAll");
        } else {//如果value不为空
            //System.out.println("从Redis中查询");
            for (String item :
                    values) {
                JSON json = (JSON) JSON.parse(item);
                BlogBean blogBean = JSON.toJavaObject(json, BlogBean.class);
                blogBeans.add(blogBean);
            }
            System.out.println("从Redis中获取GetAll");
        }

        return blogBeans;
    }

}
