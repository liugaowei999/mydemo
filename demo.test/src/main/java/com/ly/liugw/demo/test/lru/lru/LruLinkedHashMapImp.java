package com.ly.liugw.demo.test.lru.lru;

import com.ly.liugw.demo.test.lru.LruCache;
import com.ly.liugw.demo.test.lru.Storage;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class LruLinkedHashMapImp<K,V> extends LruCache<K,V> {
    private Map<K,V> map;

    /**
     * lru链表
     */
    private LinkedList<K> lruList = new LinkedList<>();

    public LruLinkedHashMapImp(int capacity, Storage<K, V> storage) {
        super(capacity, storage);
        map = new HashMap<>(capacity);
    }

    @Override
    public V get(K key) {
        //先从高速缓存中获取
        V value = map.get(key);
        if (value != null) {
            //调整元素位置
            K lastKey = lruList.peekLast();
            if (!key.equals(lastKey)) {
                lruList.remove(key);
                lruList.addLast(key);
            }
            return value;
        }
        //加锁访问低速缓存
        synchronized (this) {
            value = map.get(key);
            if (value != null) {
                return value;
            }
            //再从低速缓存中获取
            value = storage.get(key);
            if (value == null) {
                return null;
            }
            //写入高速缓存
            if (map.size()>=capacity) {
                K removeKey = lruList.removeFirst();
                map.remove(removeKey);
            }
            map.put(key, value);
            lruList.addLast(key);
        }
        return value;
    }
}
