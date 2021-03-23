package com.yincheng.game.web.controller;

import com.yincheng.game.model.Result;
import com.yincheng.game.model.anno.Authentication;
import com.yincheng.game.model.anno.CurrentUser;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.vo.LoginFacebookReq;
import com.yincheng.game.model.vo.LoginReq;
import com.yincheng.game.model.vo.UserResp;
import com.yincheng.game.service.FacebookService;
import com.yincheng.game.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

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
        // User login = loginService.login(req);
        // https://graph.facebook.com/debug_token?access_token=1165322047236078%7C4e6bf0c54658a3cefffa92e8f1751502&input_token=
        String input = "EAAQj2q6spZB4BAJIgT4AMGg01ndvClk6pugRdrotRzGw0OB4wRq02o6tyTfEaxHE9cikzf6YT6nfRTayH1SZCYNbnnYnA0JBOA3oZCAE6xGME65TIthwtUMNGjjS7oOJmfX9GUNtzAOtdBb1NtRW82NIzx6iF8C5cn2wcyhsvsa0GAB5BZCZBBCMxAYzyXRUZD";
        return Result.success(facebookService.verifyAccessToken(input));
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
