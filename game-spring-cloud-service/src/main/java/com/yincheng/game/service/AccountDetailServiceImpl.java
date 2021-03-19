package com.yincheng.game.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yincheng.game.dao.mapper.AccountDetailMapper;
import com.yincheng.game.model.po.AccountDetail;
import org.springframework.stereotype.Service;

/**
 * @author qijianguo
 */
@Service
public class AccountDetailServiceImpl extends ServiceImpl<AccountDetailMapper, AccountDetail> implements AccountDetailService{

}
