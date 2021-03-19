package com.yincheng.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yincheng.game.model.po.Account;
import com.yincheng.game.model.po.BetHistory;
import com.yincheng.game.model.po.Task;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.vo.BetAddReq;
import com.yincheng.game.model.vo.BetReq;

import java.util.List;

/**
 * @author qijianguo
 */
public interface BetHistoryService extends IService<BetHistory> {

    /**
     * 结算
     * @param result
     */
    void settle(Task result);

    /**
     * 结算
     * @param result 开奖结果
     * @param notice 是否通知
     */
    void settle(Task result, boolean notice);

    /**
     * 结算
     * @param betHistory 下注记录
     * @param result 开奖结果
     * @return
     */
    Account settle(BetHistory betHistory, List<Integer> result);


    /**
     * 下注
     * @param user
     * @param req
     */
    Account bet(User user, BetAddReq req);

    /**
     * 条件查询用户的下注记录
     * @param user
     * @param req
     * @return
     */
    List<BetHistory> list(User user, BetReq req);

}
