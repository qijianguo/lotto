package com.yincheng.game.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yincheng.game.common.util.TimeUtils;
import com.yincheng.game.model.Result;
import com.yincheng.game.model.anno.CurrentUser;
import com.yincheng.game.model.enums.AccountDetailType;
import com.yincheng.game.model.po.AccountDetail;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.vo.AccountDetailReq;
import com.yincheng.game.model.vo.NotificationResp;
import com.yincheng.game.service.AccountDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
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
    private AccountDetailService accountDetailService;

    @ApiOperation(value = "提现通知", response = NotificationResp.class)
    @GetMapping("/withdraw")
    public Result withdraw() {
        List<AccountDetail> records = accountDetailService.top(AccountDetailType.WITHDRAW);
        return Result.success(convertFromPo(records));
    }

    @ApiOperation(value = "中奖通知", response = NotificationResp.class)
    @GetMapping("/reward")
    public Result reward() {
        List<AccountDetail> records = accountDetailService.top(AccountDetailType.REWARD);
        return Result.success(convertFromPo(records));
    }

    private List<NotificationResp> convertFromPo(List<AccountDetail> poList) {
        List<NotificationResp> respList = new ArrayList<>();
        poList.forEach(po -> {
            NotificationResp resp = new NotificationResp();
            resp.setTitle("user" + po.getUserId());
            //resp.setTime(TimeUtils.mill2HourMin(System.currentTimeMillis() - po.getUpdateTime().getTime()));
            resp.setTime("now");
            resp.setDescription("Rp" + po.getCredit());
            respList.add(resp);
        });
        return respList;
    }

}
