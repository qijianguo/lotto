package com.qijianguo.game.web.interceptor;

import com.qijianguo.game.common.exception.BusinessException;
import com.qijianguo.game.common.exception.EmBusinessError;
import com.qijianguo.game.model.po.User;
import com.qijianguo.game.model.Constants;
import com.qijianguo.game.model.anno.Authentication;
import com.qijianguo.game.model.enums.Role;
import com.qijianguo.game.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义拦截器，判断此次请求是否有权限
 *
 * @author qijianguo
 */
@Component
@Slf4j
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler){
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        Authentication authentication = method.getAnnotation(Authentication.class);
        if (authentication == null) {
            return true;
        }
        User authenticatedUser = preHandler(authentication, request);
        //如果token验证成功，将token对应的用户存在request中，便于之后注入
        request.setAttribute(Constants.CURRENT_USER_ID, authenticatedUser);

        return true;
    }

    private User preHandler(Authentication authentication, HttpServletRequest request) {
        List<Role> roles = Arrays.stream(authentication.roles()).collect(Collectors.toList());
        // 根据token查询用户信息
        String token = request.getHeader(Constants.TOKEN);
        User user = tokenService.verify(token);
        if (user.getRoles() == null) {
            throw new BusinessException(EmBusinessError.USER_TOKEN_EXPIRED);
        }
        String[] split = user.getRoles().split(",");
        List<String> strRoles = Arrays.asList(split);
        boolean success = false;
        if (!CollectionUtils.isEmpty(strRoles)) {
            for (String strRole : strRoles) {
                Role role = Role.valueOf(strRole);
                if (roles.contains(role)) {
                    success = true;
                    break;
                }
            }
        }
        if (success) {
            return user;
        }
        throw new BusinessException(EmBusinessError.USER_NO_PERMISSION);
    }

}
