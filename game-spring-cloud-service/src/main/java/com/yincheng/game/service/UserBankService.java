package com.yincheng.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yincheng.game.model.po.User;
import com.yincheng.game.model.po.UserBank;
import com.yincheng.game.model.vo.BankAddReq;
import com.yincheng.game.model.vo.BankUpdateReq;

import java.util.List;

/**
 * @author qijianguo
 */
public interface UserBankService extends IService<UserBank> {

    /**
     * 添加用户银行卡信息
     * @param user 用户信息
     * @param req 银行卡信息
     * @return
     */
    UserBank add(User user, BankAddReq req);

    /**
     * 查询用户所有银行卡
     * @param user
     * @return
     */
    List<UserBank> list(User user);

    /**
     * 修改银行卡信息
     * @param req
     */
    void update(BankUpdateReq req);

    /**
     * 查询最近添加的一个
     * @param userId 用户ID
     * @return
     */
    UserBank getLatestAddOne(Integer userId);
}
