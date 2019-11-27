package com.ly.liugw.demo.test.lru.lru;

import com.ly.liugw.demo.test.common.TimeHelper;
import com.ly.liugw.demo.test.lru.LruCache;
import com.ly.liugw.demo.test.lru.Storage;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 基于Map和双向链表的 LRU容器
 * https://blog.csdn.net/hopeztm/article/details/79547052  ---- Redis LRU实现对比
 * https://www.jianshu.com/p/c4e4d55706ff
 *
 * @param <K>
 * @param <V>
 */
public class LruCacheImp<K,V> extends LruCache<K,V>{
    private Map<K,LRUNode<K,V>> map;
    private LRUNode<K,V> head;
    private LRUNode<K,V> tail;


    public LruCacheImp(int capacity, Storage<K,V> storage) {
        super(capacity, storage);
        map = new HashMap<>();
        head = null;
        tail = null;
    }

    @Override
    public synchronized V get(K key) {
        synchronized(this) {
            LRUNode<K, V> node = map.get(key);
            if (node != null) {
                // 将刚被访问的元素设置为head
                setHead(node);
                return node.value;
            }
        }
        return null;
    }

    public synchronized void set(K key, V value) {
        LRUNode<K, V> node = map.get(key);
        if (node != null) {
            // 还在LRU容器中， 则调整到头结点
            node.value = value;
        } else {
            node = new LRUNode<K, V>(key, value);
            if (map.size() >= capacity) {
                // 新元素添加时， 容量不足时，先删除最久未使用的元素
                removeTail();
            }
            map.put(key, node);
        }
        // 将刚被访问的元素设置为head
        setHead(node);
    }

    public void print() {
        LRUNode curNode = head;
        while (curNode != null) {
            System.out.println(curNode.key + "," + curNode.value);
            curNode = curNode.next;
        }
        System.out.println("head ->" + head.key + "," + head.value);
        System.out.println("tail ->" + tail.key + "," + tail.value);
        System.out.println("=====================================");
    }

    @Override
    public synchronized void remove(K key) {
        map.remove(key);
    }

    private void removeTail() {
        if (tail == null) {
            return;
        }
        map.remove(tail.key);
        LRUNode<K, V> prevNode = tail.prev;
        if (prevNode != null) {
            prevNode.next = null;
            tail.prev = null;
            tail = prevNode;
        }
    }

    /**
     * 1 2 3 4 5 6
     * -----
     */
    private void setHead(LRUNode<K,V> node) {
        if (head == node) {
            return;
        }
        if (head == null) {
            // 第一个结点
            head = node;
            tail = node;
            return;
        }

        if (tail == node) {
            tail = node.prev;
            tail.next = null;
        }

        LRUNode prevNode = node.prev;
        LRUNode nextNode = node.next;

        if (prevNode != null) {
            prevNode.next = node.next;
        }

        if (nextNode != null) {
            nextNode.prev = prevNode;
        }
        node.next = head;
        node.prev = null;
        head.prev = node;
        head = node;
    }

    private void swap(LRUNode head, LRUNode node) {
        node.prev = head.prev;
        node.next = head;
        head.next = node.next;
        head.prev = node;
    }



}

class LRUNode<K,V> {
    K key;
    V value;
    LRUNode<K,V> prev;
    LRUNode<K,V> next;

    public LRUNode(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
