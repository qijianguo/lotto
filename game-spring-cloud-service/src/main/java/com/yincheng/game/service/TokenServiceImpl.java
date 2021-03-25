package com.yincheng.game.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.model.RedisKeys;
import com.yincheng.game.model.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author qijianguo
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final long EXPIRE_DATE = 30*60*100000;

    private static final String TOKEN_SECRET = "ZCEQIUBFKSJBFJH2020BQWE";

    @Override
    public String create(User user) {
        if (user == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_ERROR);
        }
        String oldToken = user.getToken();
        if (!StringUtils.isEmpty(oldToken)) {
            remove(oldToken);
        }
        Date date = new Date(System.currentTimeMillis()+EXPIRE_DATE);
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        Map<String, Object> headers = new HashMap<>(2);
        headers.put("type", "JWT");
        headers.put("alg", "HS256");
        String token = JWT.create().withHeader(headers)
                .withClaim("userId", user.getId())
                .withExpiresAt(date)
                .sign(algorithm);
        user.setToken(token);
        redisTemplate.opsForValue().set(RedisKeys.user(user.getId()), user, EXPIRE_DATE, TimeUnit.MILLISECONDS);
        return token;
    }

    @Override
    public void remove(String token) {
        Integer userId = simpleVerify(token);
        if (userId != null) {
            redisTemplate.delete(RedisKeys.user(userId));
        }
    }

    @Override
    public User verify(String token) {
        try {
            Integer userId = simpleVerify(token);
            // 校验
            Object o = redisTemplate.opsForValue().get(RedisKeys.user(userId));
            if (o instanceof User) {
                User user = (User) o;
                String old = user.getToken();
                // 只允许最新的Token去登录
                if (Objects.equals(old, token)) {
                    return user;
                }
            }
        } catch (Exception ignore) {}
        throw new BusinessException(EmBusinessError.USER_TOKEN_EXPIRED);
    }

    @Override
    public Integer simpleVerify(String token) {
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            int userId = jwt.getClaim("userId").asInt();
            return userId;
        } catch (Exception e) {
            throw new BusinessException(EmBusinessError.USER_TOKEN_EXPIRED);
        }
    }

}
