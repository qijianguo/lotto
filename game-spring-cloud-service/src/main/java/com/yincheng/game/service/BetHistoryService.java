package com.yincheng.game.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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

    void settleAndNotice(String game, Task current, Task next);

    /**
     * 结算
     * @param result 开奖结果
     */
    void settle(Task result);

    /**
     * 结算
     * @param gameType 游戏名称
     * @param result 开奖结果
     * @param notice 是否通知
     */
    void settle(String gameType, Task result, boolean notice);

    /**
     * 结算
     * @param betHistory 下注记录
     * @param result 开奖结果
     * @return 用户账户信息
     */
    Account settle(BetHistory betHistory, List<Integer> result);


    /**
     * 下注
     * @param user 下注用户
     * @param req 下注参数
     * @return 用户账户信息
     */
    Account bet(User user, BetAddReq req);

    /**
     * 条件查询用户的下注记录
     * @param user 查询用户
     * @param req 查询参数
     * @return 分页
     */
    IPage<BetHistory> list(User user, BetReq req);

    Account saveInDb(BetHistory betHistory, Integer rewardCount, Integer betSize, Integer num);


}
