package com.qijianguo.game.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qijianguo.game.dao.mapper.GameFlowMapper;
import com.qijianguo.game.model.po.GameFlow;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 游戏流程执行服务
 * @author qijianguo
 */
@Service
public class GameFlowServiceImpl extends ServiceImpl<GameFlowMapper, GameFlow> implements GameFlowService {

    /**
     * 项目启动后自动查询DB配置，并创建游戏任务
     */
    public void initJobs() {

    }

    /**
     * 查询所有开启的
     * @return
     */
    @Override
    public List<GameFlow> getAllEnabled() {
        return lambdaQuery().eq(GameFlow::getEnabled, 1).list();
    }

}
