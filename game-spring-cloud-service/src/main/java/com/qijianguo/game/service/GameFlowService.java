package com.qijianguo.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qijianguo.game.model.po.GameFlow;

import java.util.List;

public interface GameFlowService extends IService<GameFlow> {

    List<GameFlow> getAllEnabled();
}
