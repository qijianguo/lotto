package com.yincheng.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yincheng.game.model.po.GameConfig;

import java.util.List;

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
