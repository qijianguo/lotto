package com.qijianguo.game.web.controller;

import com.qijianguo.game.model.Result;
import com.qijianguo.game.model.vo.NotificationResp;
import com.qijianguo.game.service.NotificationService;
import com.qijianguo.game.model.po.Notification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qijianguo
 */
@RestController
@RequestMapping("/notice")
@Api(tags = "通知管理")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @ApiOperation(value = "提现通知", response = NotificationResp.class)
    @GetMapping("/withdraw")
    public Result withdraw(Integer size) {
        List<Notification> withdraw = notificationService.getWithdraw(size);
        List<NotificationResp> respList = new ArrayList<>();
        withdraw.forEach(po -> {
            NotificationResp resp = new NotificationResp();
            resp.setTitle(po.getTitle());
            resp.setTime(po.getTime());
            resp.setCredit(po.getCredit());
            resp.setDescription(po.getDescription());
            respList.add(resp);
        });
        return Result.success(respList);
    }

    @ApiOperation(value = "中奖通知", response = NotificationResp.class)
    @GetMapping("/reward")
    public Result reward(Integer size) {
        List<Notification> withdraw = notificationService.getReward(size);
        List<NotificationResp> respList = new ArrayList<>();
        withdraw.forEach(po -> {
            NotificationResp resp = new NotificationResp();
            resp.setTitle(po.getTitle());
            resp.setTime(po.getTime());
            resp.setDescription(po.getDescription());
            respList.add(resp);
        });
        return Result.success(respList);
    }

}
