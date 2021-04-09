package com.yincheng.game.concurent;

import com.yincheng.game.model.po.BetHistory;

import java.util.Comparator;

/**
 * @author qijianguo
 */
public interface ThreadSubmitStrategy {

    Comparator<BetHistory> comparator();

    void add(SpiderFutureTask<?> task);

    boolean isEmpty();

    SpiderFutureTask<?> get();

}
