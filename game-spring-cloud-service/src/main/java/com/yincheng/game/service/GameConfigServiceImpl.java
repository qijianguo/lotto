package com.yincheng.game.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yincheng.game.dao.mapper.GameConfigMapper;
import com.yincheng.game.model.po.GameConfig;
import org.springframework.stereotype.Service;

/**
 * @author qijianguo
 */
@Service
public class GameConfigServiceImpl extends ServiceImpl<GameConfigMapper, GameConfig> implements GameConfigService {

    @Override
    public GameConfig getByName(String name) {
        return lambdaQuery().eq(GameConfig::getName, name).one();
    }
}
