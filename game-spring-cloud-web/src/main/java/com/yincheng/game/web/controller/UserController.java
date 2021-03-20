package com.yincheng.game.web.controller;

import com.yincheng.game.model.Result;
import com.yincheng.game.model.anno.Authentication;
import com.yincheng.game.model.anno.CurrentUser;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.vo.LoginReq;
import com.yincheng.game.service.UserService;
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
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result login(LoginReq req, HttpSession httpSession) {
        User user = userService.login(req);
        httpSession.setAttribute(user.getToken(), user);
        System.out.println("HTTP:===========" + httpSession.getId());
        return Result.success(user);
    }

    @ApiOperation(value = "退出")
    @DeleteMapping("/logout")
    @Authentication
    public Result logout(@CurrentUser User user, HttpSession httpSession) {
        httpSession.removeAttribute(user.getToken());
        return Result.success();
    }

    @ApiOperation("基本信息")
    @GetMapping("/info")
    @Authentication
    public Result info(@CurrentUser User user) {
        return Result.success(user);
    }

}
