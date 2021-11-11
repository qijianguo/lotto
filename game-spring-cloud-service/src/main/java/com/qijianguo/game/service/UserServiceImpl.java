package com.qijianguo.game.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qijianguo.game.model.po.User;
import com.qijianguo.game.dao.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * 用户管理
 * @author qijianguo
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
