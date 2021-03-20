package com.yincheng.game.web.interceptor;

import com.yincheng.game.common.exception.BusinessException;
import com.yincheng.game.common.exception.EmBusinessError;
import com.yincheng.game.model.Constants;
import com.yincheng.game.model.anno.Authentication;
import com.yincheng.game.model.enums.Role;
import com.yincheng.game.model.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        String token = request.getHeader("token");
        HttpSession session = request.getSession();
        Object o = session.getAttribute(token);
        if (o instanceof User) {
            User user = (User) o;
            Role role = Role.valueOf(user.getRole());
            if (role == Role.ADMIN) {
                // ADMIN --》 xxx --> pass
                return user;
            } else if (roles.contains(role)){
               return user;
            } else {
                //      --> USER -- pass
                throw new BusinessException(EmBusinessError.USER_NO_PERMISSION);
            }
        }
        throw new BusinessException(EmBusinessError.USER_TOKEN_EXPIRED);
    }


}
