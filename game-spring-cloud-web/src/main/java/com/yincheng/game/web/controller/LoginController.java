package com.yincheng.game.web.controller;

import com.yincheng.game.model.Result;
import com.yincheng.game.model.anno.Authentication;
import com.yincheng.game.model.anno.CurrentUser;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.vo.LoginFacebookReq;
import com.yincheng.game.model.vo.LoginReq;
import com.yincheng.game.model.vo.UserResp;
import com.yincheng.game.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @ApiOperation(value = "Facebook登录/注册")
    @PostMapping("/login/fb")
    public Result login(LoginFacebookReq req) {
        User login = loginService.login(req);
        return Result.success(new UserResp(login));
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
