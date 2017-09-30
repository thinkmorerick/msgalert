package com.alert.msgalert.utils;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 用来存储短暂对象的缓存类，实现Map接口，内部有一个定时器用来清除过期（60秒）的对象。
 * 为避免创建过多线程，没有特殊要求请使用getDefault()方法来获取本类的实例。
 * @param <K>
 * @param <V>
 */

public class CacheMap<K, V> extends AbstractMap<K, V> {

    private static final long DEFAULT_TIMEOUT = 3600000; //定时器清空时间
    private static CacheMap<String, Integer> defaultInstance;

    public static synchronized final CacheMap<String, Integer> getDefault() {
        if (defaultInstance == null) {
            defaultInstance = new CacheMap<String, Integer>(DEFAULT_TIMEOUT);
        }
        return defaultInstance;
    }

    private class CacheEntry implements Entry<K, V> {
        long time;
        V value;
        K key;

        CacheEntry(K key, V value) {
            super();
            this.value = value;
            this.key = key;
            this.time = System.currentTimeMillis();
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V value) {
            return this.value = value;
        }
    }

    private class ClearThread extends Thread {
        ClearThread() {
            setName("clear cache thread");
        }

        public void run() {
            while (true) {
                try {
                    long now = System.currentTimeMillis();
                    Object[] keys = map.keySet().toArray();
                    for (Object key : keys) {
                        CacheEntry entry = map.get(key);
                        if (now - entry.time >= cacheTimeout) {
                            synchronized (map) {
                                map.remove(key);
                            }
                        }
                    }
                    Thread.sleep(cacheTimeout);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private long cacheTimeout;
    private Map<K, CacheEntry> map = new HashMap<K, CacheEntry>();

    public CacheMap(long timeout) {
        this.cacheTimeout = timeout;
        new ClearThread().start();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entrySet = new HashSet<Map.Entry<K, V>>();
        Set<Entry<K, CacheEntry>> wrapEntrySet = map.entrySet();
        for (Entry<K, CacheEntry> entry : wrapEntrySet) {
            entrySet.add(entry.getValue());
        }
        return entrySet;
    }

    @Override
    public V get(Object key) {
        CacheEntry entry = map.get(key);
        return entry == null ? null : entry.value;
    }

    @Override
    public V put(K key, V value) {
        CacheEntry entry = new CacheEntry(key, value);
        synchronized (map) {
            map.put(key, entry);
        }
        return value;
    }

}

