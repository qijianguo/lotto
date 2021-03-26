package com.yincheng.game.web.controller;

import com.yincheng.game.model.Result;
import com.yincheng.game.model.anno.Authentication;
import com.yincheng.game.model.anno.CurrentUser;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.vo.*;
import com.yincheng.game.service.LoginService;
import com.yincheng.game.service.TokenService;
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
    private TokenService tokenService;

    @ApiOperation(value = "Facebook登录/注册")
    @GetMapping("/login/facebook")
    public Result login(LoginFacebookReq req) {
        User user = loginService.login(req);
        return Result.success(new UserResp(user));
    }

    @ApiOperation(value = "手机号登录/注册")
    @PostMapping("/login/phone")
    public Result login(LoginPhoneReq req) {
        User user = loginService.login(req);
        return Result.success(new UserResp(user));
    }

    @ApiOperation(value = "退出")
    @DeleteMapping("/logout")
    @Authentication
    public Result logout(@ApiIgnore @CurrentUser User user) {
        tokenService.remove(user.getToken());
        return Result.success();
    }

}
