package com.yincheng.game.web.interceptor;

import com.yincheng.game.model.Constants;
import com.yincheng.game.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 检查握手请求和响应, 对WebSocketHandler传递属性
 * @author qijianguo
 */
public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthHandshakeInterceptor.class);

    /**
     * 在握手之前执行该方法, 继续握手返回true, 中断握手返回false.
     * @param request
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse serverHttpResponse,
                                   WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            HttpSession session = ((ServletServerHttpRequest) request).getServletRequest().getSession();
            // TODO 校验请求参数合法性
            String token = ((ServletServerHttpRequest) request).getServletRequest().getParameter(Constants.TOKEN);
            if (!validate()) {
                return false;
            }
            attributes.put(Constants.TOKEN, token);
        }
        return true;
    }

    /**
     * 在握手之后执行该方法. 无论是否握手成功都指明了响应状态码和相应头.
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param e
     */
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                               WebSocketHandler webSocketHandler, Exception e) {

    }

    private boolean validate() {
        return true;
    }
}
