package com.yincheng.game.concurent;

import com.yincheng.game.model.GameNode;

import java.util.Comparator;

/**
 * @author qijianguo
 */
public interface ThreadSubmitStrategy {

    Comparator<GameNode> comparator();

    void add(GameFutureTask<?> task);

    boolean isEmpty();

    GameFutureTask<?> get();

}
