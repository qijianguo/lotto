package com.qijianguo.game.concurent;

import com.qijianguo.game.model.po.BetHistory;

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
