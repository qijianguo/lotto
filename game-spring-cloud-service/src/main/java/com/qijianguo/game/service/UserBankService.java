package com.qijianguo.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qijianguo.game.model.po.User;
import com.qijianguo.game.model.po.UserBank;
import com.qijianguo.game.model.vo.BankReq;

import java.util.List;

/**
 * @author qijianguo
 */
public interface UserBankService extends IService<UserBank> {

    /**
     * 添加/修改用户银行卡信息
     * @param user 用户信息
     * @param req 银行卡信息
     * @return
     */
    UserBank save(User user, BankReq req);

    /**
     * 查询用户所有银行卡
     * @param user
     * @return
     */
    List<UserBank> list(User user);


    /**
     * 查询最近添加的一个
     * @param userId 用户ID
     * @return
     */
    UserBank getLatestAddOne(Integer userId);
}
