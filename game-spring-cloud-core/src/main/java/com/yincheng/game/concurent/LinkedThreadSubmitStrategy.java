package com.yincheng.game.concurent;

import com.yincheng.game.model.GameNode;

import java.util.Comparator;

public class LinkedThreadSubmitStrategy implements ThreadSubmitStrategy {

    @Override
    public Comparator<GameNode> comparator() {
        return null;
    }

    @Override
    public void add(GameFutureTask<?> task) {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public GameFutureTask<?> get() {
        return null;
    }
}
