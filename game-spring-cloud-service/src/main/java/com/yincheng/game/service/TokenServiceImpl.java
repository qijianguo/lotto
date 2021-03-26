package com.yincheng.game.service;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.model.RedisKeys;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.vo.UserPrincipal;
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
        String userStr = JSON.toJSONString(user);
        Date date = new Date(System.currentTimeMillis()+EXPIRE_DATE);
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        Map<String, Object> headers = new HashMap<>(2);
        headers.put("type", "JWT");
        headers.put("alg", "HS256");
        String token = JWT.create().withHeader(headers)
                .withClaim("userId", user.getId())
                //.withClaim("userinfo", userStr)
                .withExpiresAt(date)
                .sign(algorithm);
        user.setToken(token);
        redisTemplate.opsForValue().set(RedisKeys.user(user.getId()), user, EXPIRE_DATE, TimeUnit.MILLISECONDS);
        return token;
    }

    @Override
    public void remove(String token) {
        User user = simpleVerify(token);
        if (user != null) {
            redisTemplate.delete(RedisKeys.user(user.getId()));
        }
    }

    @Override
    public User verify(String token) {
        UserPrincipal tokenUser = getPrincipal(token);
        if (tokenUser != null) {
            // 校验
            Object o = redisTemplate.opsForValue().get(RedisKeys.user(tokenUser.getUserId()));
            if (o instanceof User) {
                User user = (User) o;
                String old = user.getToken();
                // 只允许最新的Token去登录
                if (Objects.equals(old, token)) {
                    return user;
                }
            }
        }
        throw new BusinessException(EmBusinessError.USER_TOKEN_EXPIRED);
    }

    @Override
    public User simpleVerify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            String userId = jwt.getClaim("userId").asString();
            if (userId != null) {
                User user = new User();
                user.setId(Integer.parseInt(userId));
                return user;
            }
        } catch (Exception ignore) {}
        return null;
    }

    @Override
    public UserPrincipal getPrincipal(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            Integer userId = jwt.getClaim("userId").asInt();
            return new UserPrincipal(userId);
        } catch (Exception ignore) {}
        return null;
    }

}
