package com.qijianguo.game.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qijianguo.game.dao.mapper.GameConfigMapper;
import com.qijianguo.game.model.po.GameConfig;
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
