package com.yincheng.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yincheng.game.model.po.GameFlow;

import java.util.List;

public interface GameFlowService extends IService<GameFlow> {

    List<GameFlow> getAllEnabled();
}
