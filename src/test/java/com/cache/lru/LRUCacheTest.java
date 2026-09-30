package com.cache.lru;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class LRUCacheTest {

    @Test
    public void testLRUCache() {

        LRUCache<Integer, String> lruCache = new LRUCache<>(10);

        lruCache.put(1, "ABC");
        lruCache.put(2, "DEF");

        Assertions.assertEquals("ABC", lruCache.get(1));
        Assertions.assertEquals("DEF", lruCache.get(2));

        Assertions.assertEquals(2, lruCache.size());
    }

    @Test
    public void testLRUCache_overflow() {

        LRUCache<Integer, String> lruCache = new LRUCache<>(5);

        lruCache.put(1, "ABC");
        lruCache.put(2, "DEF");
        lruCache.put(3, "GHI");
        lruCache.put(4, "JKL");
        lruCache.put(5, "MNO");
        lruCache.put(6, "PQR");

        Assertions.assertNull(lruCache.get(1));
        Assertions.assertEquals(5, lruCache.size());
        Assertions.assertEquals("DEF", lruCache.get(2));
        Assertions.assertEquals("PQR", lruCache.get(6));
    }

    @Test
    public void testLRUCache_updateExistingKey() {

        LRUCache<Integer, String> lruCache = new LRUCache<>(5);

        lruCache.put(1, "ABC");
        lruCache.put(2, "DEF");
        lruCache.put(2, "GHI");

        Assertions.assertEquals("GHI", lruCache.get(2));
        Assertions.assertEquals(2, lruCache.size());
    }

    @Test
    public void testLRUCache_evict() {

        LRUCache<Integer, String> lruCache = new LRUCache<>(5);

        lruCache.put(1, "ABC");
        lruCache.put(2, "DEF");
        lruCache.put(3, "GHI");

        lruCache.evict();

        Assertions.assertNull(lruCache.get(1));
        Assertions.assertEquals(2, lruCache.size());
    }

    @Test
    public void testLRUCache_evictEmptyCache() {

        LRUCache<Integer, String> lruCache = new LRUCache<>(5);
        lruCache.evict();

        Assertions.assertEquals(0, lruCache.size());
    }

    @Test
    public void testLRUCache_clear() {

        LRUCache<Integer, String> lruCache = new LRUCache<>(5);

        lruCache.put(1, "ABC");
        lruCache.put(2, "DEF");
        lruCache.put(3, "GHI");

        lruCache.clear();

        Assertions.assertEquals(0, lruCache.size());
    }

    @Test
    public void testLRUCache_toString() {

        LRUCache<Integer, String> lruCache = new LRUCache<>(5);

        lruCache.put(1, "ABC");
        lruCache.put(2, "DEF");
        lruCache.put(3, "GHI");

        String str = "3 -> GHI\n" +
                "2 -> DEF\n" +
                "1 -> ABC";

        Assertions.assertEquals(str, lruCache.toString());
    }

    @Test
    public void testLRUCache_toStringEmptyCache() {

        LRUCache<Integer, String> lruCache = new LRUCache<>(5);

        Assertions.assertEquals("EMPTY", lruCache.toString());
    }

    @Test
    public void testLRUCache_isEmpty() {

        LRUCache<Integer, String> lruCache = new LRUCache<>(5);

        Assertions.assertTrue(lruCache.isEmpty());
    }

    @Test
    public void testLRUCache_putAll() {

        LRUCache<Integer, String> lruCache = new LRUCache<>(5);

        Map<Integer, String> map = new HashMap<>();

        map.put(1, "ABC");
        map.put(2, "DEF");
        map.put(3, "GHI");

        lruCache.putAll(map);

        Assertions.assertEquals(3, lruCache.size());
    }

}
