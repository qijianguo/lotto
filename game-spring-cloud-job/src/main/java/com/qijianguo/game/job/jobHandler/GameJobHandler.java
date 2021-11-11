package com.qijianguo.game.job.jobHandler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author qijianguo
 */
@Component
public class GameJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(GameJobHandler.class);

    @XxlJob("gameJobHandler")
    public void gameJobHandler() {
        logger.info("gameJobHandler start");
    }

}
