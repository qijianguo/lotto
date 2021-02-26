package com.yincheng.game.logbak;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;

/**
 * @author qijianguo
 */
public class GameFlowFileAppender extends FileAppender<ILoggingEvent> {

    @Override
    protected void subAppend(ILoggingEvent event) {

    }
}
