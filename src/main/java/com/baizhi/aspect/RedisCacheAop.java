package com.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component  //交由工厂管理
@Aspect     //声明此类为切面
public class RedisCacheAop {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //环绕通知    添加缓存 + 查询缓存
    @Around("@annotation(com.baizhi.annotation.AddCache)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //判断是否有缓存存在   有直接返回
        HashOperations hashOperations = redisTemplate.opsForHash();
        //大key namespace   小key   方法名——参数     value 值
        //获取方法名   参数
        String nameSpace = proceedingJoinPoint.getTarget().getClass().getName();//namespace
        String name = proceedingJoinPoint.getSignature().getName();//方法名
        Object[] args = proceedingJoinPoint.getArgs();//获取所有的参数
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        for (Object arg : args) {
            stringBuilder.append(arg.toString());
        }
        if (hashOperations.hasKey(nameSpace, stringBuilder)) {
            //存在    获取缓存
            System.out.println("获取缓存");
            Object o = hashOperations.get(nameSpace, stringBuilder);
            return o;
        }
        //不存在    查询数据库    并且添加缓存
        Object proceed = proceedingJoinPoint.proceed();
        System.out.println("添加缓存");
        hashOperations.put(nameSpace, stringBuilder, proceed);
        return proceed;
    }

    @After("@annotation(com.baizhi.annotation.ClearCache)")//清除韩村
    public void after(JoinPoint joinPoint) {
        //获取大key
        String name = joinPoint.getTarget().getClass().getName();
        System.out.println("清除缓存");
        /*redisTemplate.delete(name);*/
        stringRedisTemplate.delete(name);
    }
}
