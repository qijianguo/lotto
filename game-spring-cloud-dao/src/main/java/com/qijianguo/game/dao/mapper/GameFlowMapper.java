package com.qijianguo.game.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qijianguo.game.model.po.GameFlow;
import org.apache.ibatis.annotations.Param;

/**
 * @author qijianguo
 */
public interface GameFlowMapper extends BaseMapper<GameFlow> {

    int updateGameFlow(@Param("id") int id, @Param("result") String result);

}
