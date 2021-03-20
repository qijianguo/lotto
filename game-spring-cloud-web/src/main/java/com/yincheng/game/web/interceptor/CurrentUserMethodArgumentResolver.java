package com.yincheng.game.web.interceptor;

import com.yincheng.game.model.Constants;
import com.yincheng.game.model.anno.Authentication;
import com.yincheng.game.model.anno.CurrentUser;
import com.yincheng.game.model.enums.Role;
import com.yincheng.game.model.po.User;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author qijianguo
 * 增加方法注入，将含有CurrentUser注解的方法参数注入当前登录用户
 */
@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(User.class) &&
                parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication annotation = parameter.getMethod().getAnnotation(Authentication.class);
        if (annotation == null) {
            return null;
        }
        User authenticatedUser = (User) webRequest.getAttribute(Constants.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);
        if (authenticatedUser != null) {
            return authenticatedUser;
        }
        // 权限为游客访问的可传可不传
        if (Arrays.stream(annotation.roles()).collect(Collectors.toList()).contains(Role.VISITOR)) {
            return null;
        }
        throw new MissingServletRequestPartException(Constants.CURRENT_USER_ID);
    }
}
