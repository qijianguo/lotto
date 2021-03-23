package com.yincheng.game.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yincheng.game.dao.mapper.UserMapper;
import com.yincheng.game.model.po.User;
import org.springframework.stereotype.Service;

/**
 * 用户管理
 * @author qijianguo
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
