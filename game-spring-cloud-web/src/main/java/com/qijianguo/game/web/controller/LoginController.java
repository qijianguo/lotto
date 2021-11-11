package com.qijianguo.game.web.controller;

import com.qijianguo.game.model.Result;
import com.qijianguo.game.model.anno.Authentication;
import com.qijianguo.game.model.anno.CacheLock;
import com.qijianguo.game.model.anno.CurrentUser;
import com.qijianguo.game.model.po.User;
import com.qijianguo.game.model.vo.LoginFacebookReq;
import com.qijianguo.game.model.vo.LoginPhoneReq;
import com.qijianguo.game.model.vo.UserResp;
import com.qijianguo.game.service.LoginService;
import com.qijianguo.game.service.TokenService;
import com.yincheng.game.model.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
    @PostMapping("/login/facebook")
    @CacheLock(prefix = "user_login_fb")
    public Result login(LoginFacebookReq req) {
        User user = loginService.login(req);
        return Result.success(new UserResp(user));
    }

    @ApiOperation(value = "手机号登录/注册")
    @PostMapping("/login/phone")
    @CacheLock(prefix = "user_login_fb")
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
