package com.yincheng.game.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.model.po.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qijianguo
 */
@Service
public class TokenServiceImpl implements TokenService {

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

        // TODO saveInCache

        return token;
    }

    @Override
    public void remove(String token) {
        // TODO removeFromCache


    }

    @Override
    public boolean verify(User user) {
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(user.getToken());
        int userId = jwt.getClaim("userId").asInt();
        // 校验
        Date expiresAt = jwt.getExpiresAt();
        if (new Date().before(expiresAt)) {
            return true;
        }
        return false;
    }

    @Override
    public User verify(String token) {
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            int userId = jwt.getClaim("userId").asInt();
            User user = new User();
            user.setId(userId);
            return user;
        } catch (Exception e) {
            throw new BusinessException(EmBusinessError.USER_TOKEN_INVALID);
        }
    }
}
