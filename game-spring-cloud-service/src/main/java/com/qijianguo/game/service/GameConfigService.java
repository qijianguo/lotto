package com.qijianguo.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qijianguo.game.model.po.GameConfig;

/**
 * @author qijianguo
 */
public interface GameConfigService extends IService<GameConfig> {

    /**
     * 根据name查询
     * @param name
     * @return
     */
    GameConfig getByName(String name);

}
