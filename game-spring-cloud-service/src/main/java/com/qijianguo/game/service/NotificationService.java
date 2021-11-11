package com.qijianguo.game.service;

import com.qijianguo.game.model.po.User;
import com.qijianguo.game.model.po.Notification;
import com.qijianguo.game.model.vo.NotificationReq;

import java.util.List;

/**
 * @author qijianguo
 */
public interface NotificationService {

    /**
     * 获得奖励通知
     * @param req
     */
    User reward(NotificationReq req);

    /**
     * 提现通知
     * @param req
     */
    User withdraw(NotificationReq req);

    /**
     * 查询奖励通知
     * @param size 展示条数
     * @return
     */
    List<Notification> getReward(Integer size);

    /**
     * 查询奖励通知
     * @param size 展示条数
     * @return
     */
    List<Notification> getWithdraw(Integer size);

}
