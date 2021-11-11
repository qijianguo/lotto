package com.qijianguo.game.web.controller;

import com.qijianguo.game.model.Result;
import com.qijianguo.game.model.anno.Authentication;
import com.qijianguo.game.model.anno.CurrentUser;
import com.qijianguo.game.model.po.User;
import com.qijianguo.game.model.vo.UserResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


/**
 * @author qijianguo
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserController {

    @ApiOperation("基本信息")
    @GetMapping
    @Authentication
    public Result info(@ApiIgnore @CurrentUser User user) {
        return Result.success(new UserResp(user));
    }

}
