package com.qijianguo.game.common.util;

import lombok.Data;

/**
 * @author qijianguo
 */
@Data
public class Node implements Comparable<Node> {

    private Object key;

    private Object value;

    /** 总数 */
    private Integer count;

    /** nano time */
    private Long time;

    public Node(Object key, Object value, Integer count, Long time) {
        this.key = key;
        this.value = value;
        this.count = count;
        this.time = time;
    }

    @Override
    public int compareTo(Node o) {
        int compare = Integer.compare(this.count, o.count);
        if (compare != 0) {
            return compare;
        }
        return Long.compare(this.time, o.time);
    }
}
