package com.yincheng.game.web.controller;

import com.yincheng.game.model.Result;
import com.yincheng.game.model.anno.Authentication;
import com.yincheng.game.model.anno.CurrentUser;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.vo.*;
import com.yincheng.game.service.FacebookService;
import com.yincheng.game.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qijianguo
 */
@RestController
@RequestMapping("/user")
@Api(tags = "登录管理")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private FacebookService facebookService;

    @ApiOperation(value = "Facebook登录/注册")
    @GetMapping("/login/fb")
    public Result login(LoginFacebookReq req) {
        String input = "EAAQj2q6spZB4BAJIgT4AMGg01ndvClk6pugRdrotRzGw0OB4wRq02o6tyTfEaxHE9cikzf6YT6nfRTayH1SZCYNbnnYnA0JBOA3oZCAE6xGME65TIthwtUMNGjjS7oOJmfX9GUNtzAOtdBb1NtRW82NIzx6iF8C5cn2wcyhsvsa0GAB5BZCZBBCMxAYzyXRUZD";
        String accessToken = facebookService.getAccessToken();
        FacebookDebugTokenResp debugToken = facebookService.verifyAccessToken(input);
        FacebookUserResp userInfoByInputToken = facebookService.getUserInfo(input);
        Map<String, Object> map = new HashMap<>();
        map.put("token", accessToken);
        map.put("debugTokenResp", debugToken);
        map.put("userInfoByInputToken", userInfoByInputToken);
        return Result.success(map);
    }

    @ApiOperation(value = "登录/注册")
    @PostMapping("/login")
    public Result login(LoginReq req, HttpSession httpSession) {
        User user = loginService.login(req);
        httpSession.setAttribute(user.getToken(), user);
        return Result.success(new UserResp(user));
    }

    @ApiOperation(value = "退出")
    @DeleteMapping("/logout")
    @Authentication
    public Result logout(@ApiIgnore @CurrentUser User user, HttpSession httpSession) {
        httpSession.removeAttribute(user.getToken());
        return Result.success();
    }

}
