package com.qijianguo.game.web.interceptor;

import com.qijianguo.game.common.exception.BusinessException;
import com.qijianguo.game.common.exception.EmBusinessError;
import com.qijianguo.game.model.anno.CacheLock;
import com.qijianguo.game.service.CacheKeyGenerator;
import com.qijianguo.game.service.RedisLockHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * redis 方案
 * @author qijianguo
 */
@Aspect
@Configuration
@Slf4j
public class LockMethodInterceptor {

    private final RedisLockHelper redisLockHelper;
    private final CacheKeyGenerator cacheKeyGenerator;

    public LockMethodInterceptor(RedisLockHelper redisLockHelper, CacheKeyGenerator cacheKeyGenerator) {
        this.redisLockHelper = redisLockHelper;
        this.cacheKeyGenerator = cacheKeyGenerator;
    }

    @Around("execution(public * *(..)) && @annotation(com.yincheng.game.model.anno.CacheLock)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        log.info("获取锁");
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        CacheLock lock = method.getAnnotation(CacheLock.class);
        if (StringUtils.isEmpty(lock.prefix())) {
            throw new RuntimeException("lock key don't null...");
        }
        final String lockKey = cacheKeyGenerator.getLockKey(pjp);
        String value = UUID.randomUUID().toString();
        try {
            // 假设上锁成功，但是设置过期时间失效，以后拿到的都是 false
            final boolean success = redisLockHelper.lock(lockKey, value, lock.expire(), lock.timeUnit());
            if (!success) {
                throw new BusinessException(EmBusinessError.REPEAT_COMMIT_ERROR);
            }
            try {
                return pjp.proceed();
            } catch (Throwable e) {
                if (e instanceof BusinessException) {
                    throw (BusinessException)e;
                }
                log.error(e.getMessage(), e);
                throw new BusinessException(EmBusinessError.UNKNOW_ERROR);
            }
        } finally {
            redisLockHelper.unlock(lockKey, value);
            log.info("释放锁");
        }
    }
}