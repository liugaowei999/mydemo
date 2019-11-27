package com.ly.liugw.demo.test.lru;

/** * LRU缓存。你需要继承这个抽象类来实现LRU缓存。
 * @param <K> 数据Key
 * @param <V> 数据值
 */
public abstract class LruCache<K,V> implements Storage<K,V>{
    // 缓存容量 
    protected final int capacity;

    // 低速存储，所有的数据都可以从这里读到 
    protected final Storage<K,V> storage;

    public LruCache(int capacity, Storage<K,V> storage) {
        this.capacity = capacity;
        this.storage = storage;
    }

    public abstract void set(K key, V value);

    public abstract void print();

    public abstract void remove(K key);

}