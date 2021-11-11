package com.qijianguo.game.executor;

import com.qijianguo.game.context.GameContext;
import com.qijianguo.game.listener.GameListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author qijianguo
 */
@Component
public class ResultExecutor implements GameExecutor, GameListener {

    private Logger logger = LoggerFactory.getLogger(ResultExecutor.class);

    @Override
    public void execute() {
    }

    @Override
    public void beforeStart(GameContext context) {

    }

    @Override
    public void afterStop(GameContext context) {
        logger.info("calculate result！");
    }
}
