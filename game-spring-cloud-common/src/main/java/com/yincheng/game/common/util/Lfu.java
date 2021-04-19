package com.yincheng.game.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author qijianguo
 * LFU 缓存策略算法
 */
public class Lfu<K, V> {

    private Logger logger = LoggerFactory.getLogger(Lfu.class);

    private int capacity;

    private Map<K, Node> caches;

    public Lfu(int size) {
        if(size <= 0) {
            throw new IllegalArgumentException("size must bigger than zero.");
        }
        this.capacity = size;
        this.caches = new LinkedHashMap<>(size);
    }

    /**
     * 添加节点， 如果节点存在，则计数器+1，否则新增一个节点
     * @param key 节点唯一标识
     * @param value 节点的值
     */
    public void put(K key, V value) {
        Node node = caches.get(key);
        if (node == null) {
            if (caches.size() >= capacity) {
                removeMinCountNode();
            }
            caches.put(key, new Node(key, value, 1, System.nanoTime()));
        } else {
            node.setValue(value);
            node.setCount(node.getCount() + 1);
            node.setTime(System.nanoTime());
        }
        sortNode();
    }

    /**
     * 根据最近最小使用倒序排列
     */
    private void sortNode() {
        List<Map.Entry<K, Node>> list = new ArrayList<>(caches.entrySet());
        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));
        caches.clear();
        list.forEach(kNodeEntry -> caches.put(kNodeEntry.getKey(), kNodeEntry.getValue()));
    }

    /**
     * 移除最近最少使用的NODE
     */
    private void removeMinCountNode() {
        Node latestNode = getMinCountNode();
        Node remove = caches.remove(latestNode.getKey());
        if (logger.isDebugEnabled()) {
            logger.debug("removeMinCountNode: {}", remove);
        }
    }

    /**
     * 获取最近最常使用的节点
     * @return
     */
    public Node getMaxCountNode() {
        Collection<Node> values = caches.values();
        Node max = Collections.max(values);
        return max;
    }

    /**
     * 获取最远最不常使用的节点
     * @return
     */
    public Node getMinCountNode() {
        Collection<Node> values = caches.values();
        Node min = Collections.min(values);
        return min;
    }

    public static void main(String[] args) {
        /*Lfu lfu = new Lfu(3);
        for (int i = 0; i < 100; i++) {
            Integer integer = RandomUtils.secureRandomNum();
            lfu.put(integer, integer);
        }
        System.out.println(lfu.caches);*/
        int gameId = 1;
        long period = 2021030110001L;
        List<String> targets = Arrays.asList("A", "B", "C", "D", "SUM");
        Map<String, Lfu> map= new ConcurrentHashMap<>();
        // gameId-period-target,
        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            String target = targets.get(random.nextInt(targets.size()));
            String key = gameId + (period + target);
            Lfu lfu = map.get(key);
            if (lfu == null) {
                lfu = new Lfu(10);
                map.put(key, lfu);
            }
            int i1 = random.nextInt(10);
            lfu.put(target + i1, i1);
        }
        for (Lfu value : map.values()) {
            System.out.println("min" + value.getMinCountNode());
            System.out.println("max" + value.getMaxCountNode());
        }
        // 如果最小和最大相等，则直接排除掉


    }
}
